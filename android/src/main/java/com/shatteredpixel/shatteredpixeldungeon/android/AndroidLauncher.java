/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewConfiguration;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.backends.android.AsynchronousAndroidAudio;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GooglePlayGames;
import com.shatteredpixel.shatteredpixeldungeon.Rankings;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Journal;
import com.shatteredpixel.shatteredpixeldungeon.services.news.News;
import com.shatteredpixel.shatteredpixeldungeon.services.news.NewsImpl;
import com.shatteredpixel.shatteredpixeldungeon.services.updates.UpdateImpl;
import com.shatteredpixel.shatteredpixeldungeon.services.updates.Updates;
import com.shatteredpixel.shatteredpixeldungeon.ui.Button;
import com.watabou.input.KeyEvent;
import com.watabou.noosa.Game;
import com.watabou.utils.FileUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AndroidLauncher extends AndroidApplication implements GooglePlayGames {
	
	public static AndroidApplication instance;
	
	private static AndroidPlatformSupport support;

	private final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor();
	
	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			GdxNativesLoader.load();
			FreeType.initFreeType();
		} catch (Exception e){
			AndroidMissingNativesHandler.error = e;
			Intent intent = new Intent(this, AndroidMissingNativesHandler.class);
			startActivity(intent);
			finish();
			return;
		}

		//there are some things we only need to set up on first launch
		if (instance == null) {

			instance = this;

			try {
				Game.version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			} catch (PackageManager.NameNotFoundException e) {
				Game.version = "???";
			}
			try {
				Game.versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
			} catch (PackageManager.NameNotFoundException e) {
				Game.versionCode = 0;
			}

			Gdx.app = this;
			if (UpdateImpl.supportsUpdates()) {
				Updates.service = UpdateImpl.getUpdateService();
			}
			if (NewsImpl.supportsNews()) {
				News.service = NewsImpl.getNewsService();
			}

			FileUtils.setDefaultFileProperties(Files.FileType.Local, "");

			// grab preferences directly using our instance first
			// so that we don't need to rely on Gdx.app, which isn't initialized yet.
			// Note that we use a different prefs name on android for legacy purposes,
			// this is the default prefs filename given to an android app (.xml is automatically added to it)
			SPDSettings.set(instance.getPreferences("ShatteredPixelDungeon"));

		} else {
			instance = this;
		}

		//Shattered still overrides the back gesture behaviour, but we need to do it in a new way
		// (API added in Android 13, functionality enforced in Android 16)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			getOnBackInvokedDispatcher().registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT, new OnBackInvokedCallback() {
				@Override
				public void onBackInvoked() {
					KeyEvent.addKeyEvent(new KeyEvent(Input.Keys.BACK, true));
					KeyEvent.addKeyEvent(new KeyEvent(Input.Keys.BACK, false));
				}
			});
		}

		//set desired orientation (if it exists) before initializing the app.
		if (SPDSettings.landscape() != null) {
			instance.setRequestedOrientation( SPDSettings.landscape() ?
					ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE :
					ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT );
		}
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.depth = 0;
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
			//use rgb565 on ICS devices for better performance
			config.r = 5;
			config.g = 6;
			config.b = 5;
		}

		//we manage this ourselves
		config.useImmersiveMode = false;
		
		config.useCompass = false;
		config.useAccelerometer = false;
		
		if (support == null) support = new AndroidPlatformSupport();
		else                 support.reloadGenerators();
		
		support.updateSystemUI();

		Button.longClick = ViewConfiguration.getLongPressTimeout()/1000f;
		
		initialize(new ShatteredPixelDungeon(support, this), config);
		
	}

	@Override
	public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config) {
		return new AsynchronousAndroidAudio(context, config);
	}

	@Override
	protected void onResume() {
		//prevents weird rare cases where the app is running twice
		if (instance != this){
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				finishAndRemoveTask();
			} else {
				finish();
			}
		}
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		//do nothing, game should catch all back presses
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		support.updateSystemUI();
	}
	
	@Override
	public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
		super.onMultiWindowModeChanged(isInMultiWindowMode);
		support.updateSystemUI();
	}

	/* --- Google Play Games --- */

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (backgroundExecutor != null && !backgroundExecutor.isShutdown()) {
			backgroundExecutor.shutdown(); // 액티비티 종료 시 ExecutorService 종료
		}
	}

	//GooglePlayGames 인터페이스 구현, 이렇게 하는 이유는 core 폴더에 있는 파일들은 android 폴더에 있는 클래스에서 직접적으로 사용할 수 없기 때문이다.
	//반대는 가능하기 때문에, 이것을 구현한 인스턴스를 ShatteredPixelDungeon의 생성자에 담아서 보낸다.
	@Override
	public void launchGooglePlayGames(boolean isUpload) {
		// 백그라운드 스레드에서 실행
		backgroundExecutor.execute(() -> {
			//업로드 혹은 다운로드 실행
			if (isUpload) {
				saveUpload();
			} else {
				saveDownload();
			}
		});
	}

	public void saveDownload() {
		//스냅샷에서 바이트 배열 데이터를 가져온다.
		loadSnapshot().addOnSuccessListener(bytes -> {
			//데이터가 있어요!
			Gdx.app.log("REARRANGED", "Snapshot loaded successfully, bytes:" + Arrays.toString(bytes));
			Gdx.app.postRunnable(() -> {
				if (bytes != null) {
					//이제 데이터를 변환해서 게임 세이브에 저장한다.
					writeData(bytes);
				}
			});
		}).addOnFailureListener(e -> {
			Gdx.app.log("REARRANGED", "Failed to load snapshot", e);
		});
	}

	public void saveUpload() {
		//Google Play Games에 로그인 및 로그인 정보를 가져오기 위한 코드들

		//초기화
		PlayGamesSdk.initialize(this);

		//로그인 정보를 가져와 저장한다.
		GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(this);

		//로그인 정보를 성공적으로 가져왔다면 isAuthenticated가 true가 된다.
		gamesSignInClient.isAuthenticated().addOnCompleteListener(isAuthenticatedTask -> {
			boolean isAuthenticated =
					(isAuthenticatedTask.isSuccessful() &&
							isAuthenticatedTask.getResult().isAuthenticated());

			if (isAuthenticated) {
				Gdx.app.log("REARRANGED", "Google Play Games Authenticated");
				//스냅샷 클라이언트 정보를 가져온다.
				//스냅샷 클라이언트란 google play games에서 데이터를 저장하는 세이브파일이라고 보면 됨.
				//이름을 다르게 해서 여러 개 저장할 수 있지만 여기에서는 rearranged_save 하나만 쓴다.
				SnapshotsClient snapshotsClient = PlayGames.getSnapshotsClient(this);
				Gdx.app.log("REARRANGED", "Client: "+snapshotsClient);

				String snapshotName = "rearranged_save"; //스냅샷 이름을 rearranged_save로 저장
				boolean createIfMissing = true; //스냅샷이 없으면 새로 만들라고 지시하는 플래그.
				int conflictResolutionPolicy = SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED; // 충돌 시 정책

				//스냅샷을 열어 확인하는 과정
				//태스크 = 비동기 작업을 나타내는 객체
				Task<SnapshotsClient.DataOrConflict<Snapshot>> openTask =
						snapshotsClient.open(snapshotName, createIfMissing, conflictResolutionPolicy);

				//스냅샷 열기가 끝나면 실행되는 콜백
				openTask.addOnCompleteListener(task -> {
					//스냅샷 열기가 성공적이었다면 다음 작업을 진행한다.
					if (task.isSuccessful()) {
						//스냅샷에 들어 있는 데이터 혹은 충돌
						SnapshotsClient.DataOrConflict<Snapshot> dataOrConflict = task.getResult();

						if (dataOrConflict != null) {
							//충돌이 있다면 충돌 정책을 실행하고 작업을 종료한다.
							if (dataOrConflict.isConflict()) {
								SnapshotsClient.SnapshotConflict conflict = dataOrConflict.getConflict();
								Gdx.app.log("REARRANGED", "Conflict occurred: " + conflict.getConflictId());
								return;
							}

							//충돌이 없다면 데이터를 가져온다.
							Snapshot snapshotToWrite = dataOrConflict.getData();
							if (snapshotToWrite != null) {
								//스냅샷 데이터가 있다!
								Gdx.app.log("REARRANGED", "Snapshot is present");
								//업로드를 위해 데이터를 저장한다.
								createSave();
								//랭킹, 뱃지, 저널의 데이터를 가져온다.
								FileHandle rankingFile = getSaveFile(Rankings.RANKINGS_FILE);
								FileHandle badgesFile = getSaveFile(Badges.BADGES_FILE);
								FileHandle journalFile = getSaveFile(Journal.JOURNAL_FILE);

								//가져온 데이터를 바이트 배열로 변환한다.
								byte[] rankingDataBytes = rankingFile.readBytes();
								byte[] badgesDataBytes = badgesFile.readBytes();
								byte[] journalDataBytes = journalFile.readBytes();

								Gdx.app.log("REARRANGED", "Data are all read");

								//3개의 바이트 배열을 1개의 바이트 배열로 만든다.
								byte[] combinedData = combineThreeByteArrays(rankingDataBytes, badgesDataBytes, journalDataBytes);
								Gdx.app.log("REARRANGED", "Data binded");

								//구글 플레이에 보낼 SnapshotContents 객체를 만들어 안에 데이터를 넣는다.
								//SnapshotContents는 일종의 우주선 같은 거라고 생각하면 쉽다.
								//snapshotToWrite가 우주에 보낼 사람, 데이터 등이라면 snapshotContents는 우주선이다.
								SnapshotContents snapshotContents = snapshotToWrite.getSnapshotContents();
								//우주선을 만들어서 안에 데이터를 집어넣자. 구글 플레이 게임즈에 데이터를 보낼 때에는 **반드시** byte[] 배열이어야 한다.
								snapshotContents.writeBytes(combinedData);

								//스냅샷을 보내고 및 작업을 종료한다.
								writeSnapshot(snapshotToWrite, combinedData, "game_data").addOnCompleteListener(commitTask -> {
									if (commitTask.isSuccessful()) {
										//우주에서 잘 받았다고 연락이 왔다!
										Gdx.app.log("REARRANGED", "Snapshot committed and closed successfully");
									}
								});
							}
						}
					}
				});
			} else {
				// Disable your integration with Play Games Services or show a
				// login button to ask  players to sign-in. Clicking it should
				// call GamesSignInClient.signIn().
				Gdx.app.log("REARRANGED", "Google Play Games Not Authenticated");
			}
		});
	}

	//데이터를 실제로 구글에 보내는 메서드
	private Task<SnapshotMetadata> writeSnapshot(Snapshot snapshot, byte[] data, String desc) {
		// Set the data payload for the snapshot
		snapshot.getSnapshotContents().writeBytes(data);

		// Create the change operation
		SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
				.setDescription(desc)
				.build();

		SnapshotsClient snapshotsClient =
				PlayGames.getSnapshotsClient(this);

		// Commit the operation
		return snapshotsClient.commitAndClose(snapshot, metadataChange);
	}

	//구글에서 스냅샷을 받아오는 메서드
	Task<byte[]> loadSnapshot() {
		//저장할 때와 마찬가지로 스냅샷 클라이언트 정보를 가져온다.
		SnapshotsClient snapshotsClient = PlayGames.getSnapshotsClient(this);

		int conflictResolutionPolicy = SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED; //충돌 시 정책

		//마찬가지로 rearranged_save라는 이름의 스냅샷을 연다.
		String snapshotName = "rearranged_save";

		//이 코드는 구글 플레이 게임즈 API 문서에 로그만 다르고 그대로 있다.
		//https://developer.android.com/games/pgs/android/saved-games?hl=ko&_gl=1*10etqd7*_up*MQ..*_ga*NDc2NjI3NzU3LjE3NTMzMjIzMDE.*_ga_6HH9YJMN9M*czE3NTMzMjg3NjUkbzIkZzAkdDE3NTMzMjg3NjUkajYwJGwwJGg5MTI5NjUzMjI.#load-saved-games
		return snapshotsClient.open(snapshotName, true, conflictResolutionPolicy)
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Gdx.app.log("REARRANGED", "Error while opening Snapshot.", e);
					}
				}).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
					@Override
					public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
						Snapshot snapshot = task.getResult().getData();

						// Opening the snapshot was a success and any conflicts have been resolved.
						try {
							// Extract the raw data from the snapshot.
							return snapshot.getSnapshotContents().readFully();
						} catch (IOException e) {
							Gdx.app.log("REARRANGED", "Error while reading Snapshot.", e);
						}

						return null;
					}
				}).addOnCompleteListener(new OnCompleteListener<byte[]>() {
					@Override
					public void onComplete(@NonNull Task<byte[]> task) {
						// Dismiss progress dialog and reflect the changes in the UI when complete.
						// ...
						Gdx.app.log("REARRANGED", "Snapshot loaded successfully");
					}
				});
	}

	//3개의 바이트 배열을 1개의 바이트 배열로 만드는 메서드.
	public static byte[] combineThreeByteArrays(byte[] array1, byte[] array2, byte[] array3) {
		// 1. 각 배열의 길이
		int length1 = array1.length;
		int length2 = array2.length;
		int length3 = array3.length;

		// 2. 총 필요한 크기 계산 (각 길이를 int(4바이트)로 저장 + 실제 데이터)
		int totalSize = 4 + length1 + 4 + length2 + 4 + length3;
		ByteBuffer buffer = ByteBuffer.allocate(totalSize);

		// 3. 길이와 데이터를 순서대로 버퍼에 넣기
		buffer.putInt(length1);
		buffer.put(array1);

		buffer.putInt(length2);
		buffer.put(array2);

		buffer.putInt(length3);
		buffer.put(array3);

		return buffer.array(); // 최종 합쳐진 바이트 배열
	}

	public static void writeData(byte[] combinedData) {
		ByteBuffer buffer = ByteBuffer.wrap(combinedData); // 읽기용으로 랩핑

		//length는 배열 3개를 합칠 당시에 배열의 길이를 저장한 정수로, 4바이트를 사용한다.
		//array는 length 길이만큼 바이트 배열을 읽어서 저장한 배열이다. 즉 저장할 때 그대로 되돌린 것이다.
		//ByteBuffer는 배열을 읽어온 만큼 다음에 읽을 위치가 이동하기 때문에, 따로 어디에서부터 읽으라고 지정할 필요가 없다. 처음부터 4칸을 읽었다면 그 이후에 읽을 때는 5번째부터 읽는다.
		int length1 = buffer.getInt();
		byte[] array1 = new byte[length1];
		buffer.get(array1);

		//읽어 온 바이트 배열을 랭킹 파일에 각각 덮어씌운다.
		FileUtils.getFileHandle( Rankings.RANKINGS_FILE ).writeBytes(array1, 0, length1, false);
		try {
			com.watabou.utils.Bundle bundle1 = FileUtils.bundleFromFile(Rankings.RANKINGS_FILE);
			Gdx.app.log("REARRANGED", "Ranking Bundle: "+ bundle1);

			//게임 내 랭킹 데이터를 (방금 저장한) 파일에서 강제로 불러온다.
			Rankings.INSTANCE.forceLoad(bundle1);
		} catch (IOException e) {
			Gdx.app.log("REARRANGED", "Failed to make bundle from RANKING file");
			return;
		}

		//뱃지 데이터에 대해 위 작업 반복
		int length2 = buffer.getInt();
		byte[] array2 = new byte[length2];
		buffer.get(array2);
		Gdx.app.log("REARRANGED", "Badge Data: "+ Arrays.toString(array2));
		FileUtils.getFileHandle( Badges.BADGES_FILE ).writeBytes(array2, 0, length2, false);
		try {
			com.watabou.utils.Bundle bundle2 = FileUtils.bundleFromFile(Badges.BADGES_FILE);
			Gdx.app.log("REARRANGED", "Badge Bundle: "+ bundle2);

			Badges.forceLoad(bundle2);
		} catch (IOException e) {
			Gdx.app.log("REARRANGED", "Failed to make bundle from BADGE file");
			return;
		}

		//저널 데이터에 대해 위 작업 반복
		int length3 = buffer.getInt();
		byte[] array3 = new byte[length3];
		buffer.get(array3);
		Gdx.app.log("REARRANGED", "Journal Data: "+ Arrays.toString(array3));
		FileUtils.getFileHandle( Journal.JOURNAL_FILE ).writeBytes(array3, 0, length3, false);
		try {
			com.watabou.utils.Bundle bundle3 = FileUtils.bundleFromFile(Badges.BADGES_FILE);
			Gdx.app.log("REARRANGED", "Journal Bundle: "+ bundle3);

			Journal.forceLoad(bundle3);
		} catch (IOException e) {
			Gdx.app.log("REARRANGED", "Failed to make bundle from JOURNAL file");
			return;
		}

		//데이터 저장 끝
		Gdx.app.log("REARRANGED", "Data is written successfully");
	}

	private static void createSave() {
		try {
			Rankings.INSTANCE.load();
			for (Rankings.Record rec : Rankings.INSTANCE.records.toArray(new Rankings.Record[0])){
				try {
					Rankings.INSTANCE.loadGameData(rec);
					Rankings.INSTANCE.saveGameData(rec);
				} catch (Exception e) {
					//if we encounter a fatal per-record error, then clear that record's data
					rec.gameData = null;
					Game.reportException( new RuntimeException("Rankings Updating Failed!",e));
				}
			}
			if (Rankings.INSTANCE.latestDaily != null){
				try {
					Rankings.INSTANCE.loadGameData(Rankings.INSTANCE.latestDaily);
					Rankings.INSTANCE.saveGameData(Rankings.INSTANCE.latestDaily);
				} catch (Exception e) {
					//if we encounter a fatal per-record error, then clear that record's data
					Rankings.INSTANCE.latestDaily.gameData = null;
					Game.reportException( new RuntimeException("Rankings Updating Failed!",e));
				}
			}
			Collections.sort(Rankings.INSTANCE.records, Rankings.scoreComparator);
			Rankings.INSTANCE.save();
		} catch (Exception e) {
			//if we encounter a fatal error, then just clear the rankings
			FileUtils.deleteFile( Rankings.RANKINGS_FILE );
			Game.reportException( new RuntimeException("Rankings Updating Failed!",e));
		}
		Dungeon.daily = Dungeon.dailyReplay = false;

		Badges.saveGlobal(true);
		Journal.saveGlobal(true);
	}

	private static FileHandle getSaveFile(String fileName) {
        return FileUtils.getFileHandle(fileName);
	}
}