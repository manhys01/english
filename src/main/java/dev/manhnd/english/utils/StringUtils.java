package dev.manhnd.english.utils;

public class StringUtils {

	public static String format(String text) {
		if (text == null)
			return null;
		String[] arr = text.replaceAll("\\s+", " ").trim().split(" ");
		StringBuilder builder = new StringBuilder();
		for (String w : arr) {
			if (w.contains("’"))
				w = w.replace("’", "'");
			if (w.matches("[,.:!?']")) {
				builder.append(w);
				continue;
			}
			builder.append(" " + w);
		}
		return builder.toString().trim();
	}

	public static String formatIPA(String ipa) {
		if (ipa == null)
			return null;
		if (ipa.endsWith(".") || ipa.endsWith("?") || ipa.endsWith("!")) {
			ipa = ipa.substring(0, ipa.length() - 1);
		}
		ipa = ipa.replaceAll("\\s+", " ").replaceAll("\\/+", "").trim();
		return "/" + ipa.toString() + "/";
	}

}
