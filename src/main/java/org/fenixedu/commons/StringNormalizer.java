package org.fenixedu.commons;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class StringNormalizer {
    public static String normalizeAndRemoveAccents(String text) {
        return Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String normalizePreservingCapitalizedLetters(String string) {
        return Normalizer.normalize(string, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String normalize(String string) {
        return normalizePreservingCapitalizedLetters(string).toLowerCase();
    }
}
