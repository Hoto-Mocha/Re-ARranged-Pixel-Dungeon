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

package com.shatteredpixel.shatteredpixeldungeon.messages;

import java.util.Locale;

public enum Languages {
	KOREAN("한국어",      	"ko", 	Status.O_COMPLETE, null, null),
	ENGLISH("english",      "",   	Status.X_UNFINISH, new String[]{"Cocoa(Hoto-Mocha)"}, new String[]{"Cocoa(Hoto-Mocha)", "ChatGPT", "CykoRex, TrashboxBobylev"}),
	RUSSIAN("русский",      "ru", 	Status.X_UNFINISH, new String[]{"Dominowood371", "Alexandr Klimov", "Abama Abama", "Aleksey Petrenko"}, new String[]{"SPD's translators credit"}),
	CHI_SMPL("简体中文",     "zh", 	Status.X_UNFINISH, new String[]{"Bindweed", "Eygy6556", "fadedwater", "nupnuts", "wangsenxing"}, new String[]{"SPD's translators credit"}),
	SPANISH("español",      "es", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	PORTUGUESE("português", "pt", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	GERMAN("deutsch",       "de", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	FRENCH("français",      "fr", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	JAPANESE("日本語",       "ja", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	CHI_TRAD("繁體中文","zh-hant", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	POLISH("polski",        "pl", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	VIETNAMESE("tiếng việt","vi", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	TURKISH("türkçe",       "tr", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	INDONESIAN("indonésien","in", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	ITALIAN("italiano",	  "it", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	UKRANIAN("українська",  "uk", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	CZECH("čeština",        "cs", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	HUNGARIAN("magyar",     "hu", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	DUTCH("nederlands",     "nl", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	GREEK("ελληνικά",       "el", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	BELARUSIAN("беларуская","be", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"}),
	ESPERANTO("esperanto",  "eo", 	Status.X_UNFINISH, new String[]{""}, new String[]{"SPD's translators credit"});

	public enum Status{
		//below 80% translated languages are not added or removed
		X_UNFINISH, //unfinished, ~80-99% translated
		__UNREVIEW, //unreviewed, but 100% translated
		O_COMPLETE, //complete, 100% reviewed
	}

	private String name;
	private String code;
	private Status status;
	private String[] reviewers;
	private String[] translators;

	Languages(String name, String code, Status status, String[] reviewers, String[] translators){
		this.name = name;
		this.code = code;
		this.status = status;
		this.reviewers = reviewers;
		this.translators = translators;
	}

	public String nativeName(){
		return name;
	}

	public String code(){
		return code;
	}

	public Status status(){
		return status;
	}

	public String[] reviewers() {
		if (reviewers == null) return new String[]{};
		else return reviewers.clone();
	}

	public String[] translators() {
		if (translators == null) return new String[]{};
		else return translators.clone();
	}

	public static Languages matchLocale(Locale locale){
		//special case for chinese traditional, which matches more specifically than other languages
		if (locale.getLanguage().equals("zh") && locale.toString().contains("Hant")){
			return Languages.CHI_TRAD;
		}
		return matchCode(locale.getLanguage());
	}

	public static Languages matchCode(String code){
		for (Languages lang : Languages.values()){
			if (lang.code().equals(code))
				return lang;
		}
		return ENGLISH;
	}

}
