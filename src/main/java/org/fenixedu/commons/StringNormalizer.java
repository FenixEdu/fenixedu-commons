/**
 * Copyright (c) 2013, Instituto Superior Técnico. All rights reserved.
 *
 * This file is part of fenixedu-commons.
 *
 * fenixedu-commons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fenixedu-commons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fenixedu-commons. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.commons;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringNormalizer {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String normalizeAndRemoveAccents(String text) {
        return Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String normalizePreservingCapitalizedLetters(String string) {
        return Normalizer.normalize(string, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String normalize(String string) {
        return normalizePreservingCapitalizedLetters(string).toLowerCase();
    }

    public static String slugify(String name) {
        String result = normalizePreservingCapitalizedLetters(normalizeAndRemoveAccents(name.trim()));
        result = WHITESPACE.matcher(result).replaceAll("-");
        result = NONLATIN.matcher(result).replaceAll("");
        return result.toLowerCase(Locale.ENGLISH);
    }
}
