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
package org.fenixedu.commons.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

public class LocalizedStringTest {
    private static final Locale pt = Locale.forLanguageTag("pt");
    private static final Locale ptPT = Locale.forLanguageTag("pt-PT");
    private static final Locale enGB = Locale.forLanguageTag("en-GB");
    private static final Locale enUS = Locale.forLanguageTag("en-US");
    private static final Locale esES = Locale.forLanguageTag("es-ES");
    private static final Locale ptBR = Locale.forLanguageTag("pt-BR");

    @BeforeClass
    public static void setupDefaultLocale() {
        Locale.setDefault(enGB);
    }

    @Test
    public void testBuilderCreation() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();
        assertEquals(hello.getContent(ptPT), "olá");
        assertEquals(hello.getContent(enGB), "hello");
        assertTrue(hello.getLocales().contains(ptPT));
        assertTrue(hello.getLocales().contains(enGB));
        assertTrue(hello.getLocales().size() == 2);
    }

    @Test
    public void testShortcutCreation() {
        LocalizedString hello = new LocalizedString(ptPT, "olá");
        assertEquals(hello.getContent(ptPT), "olá");
        assertTrue(hello.getLocales().contains(ptPT));
        assertTrue(hello.getLocales().size() == 1);
    }

    @Test
    public void testEdition() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();

        LocalizedString helloJohn = hello.builder().append(" John").build();
        assertEquals(helloJohn.getContent(ptPT), "olá John");
        assertEquals(helloJohn.getContent(enGB), "hello John");

        LocalizedString formalHello =
                hello.builder().append(new LocalizedString.Builder().with(ptPT, " Sr.").with(enGB, " Mr.").build()).build();
        assertEquals(formalHello.getContent(ptPT), "olá Sr.");
        assertEquals(formalHello.getContent(enGB), "hello Mr.");

        LocalizedString helloPlusSpanish = hello.builder().with(esES, "hola").build();
        assertEquals(helloPlusSpanish.getContent(ptPT), "olá");
        assertEquals(helloPlusSpanish.getContent(enGB), "hello");
        assertEquals(helloPlusSpanish.getContent(esES), "hola");

        LocalizedString helloMinusEnglish = hello.builder().without(enGB).build();
        final Locale currentLocale = I18N.getLocale();

        if (ptPT.equals(currentLocale)) {
            assertEquals(helloMinusEnglish.getContent(enGB), "olá");
        } else {
            assertNull(helloMinusEnglish.getContent(enGB));
        }

    }

    @Test
    public void testShortcutEditions() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();

        LocalizedString helloJohn = hello.append(" John");
        assertEquals(helloJohn.getContent(ptPT), "olá John");
        assertEquals(helloJohn.getContent(enGB), "hello John");

        LocalizedString formalHello = hello.append(new LocalizedString(ptPT, " Sr.").with(enGB, " Mr."));
        assertEquals(formalHello.getContent(ptPT), "olá Sr.");
        assertEquals(formalHello.getContent(enGB), "hello Mr.");

        LocalizedString helloPlusSpanish = hello.with(esES, "hola");
        assertEquals(helloPlusSpanish.getContent(ptPT), "olá");
        assertEquals(helloPlusSpanish.getContent(enGB), "hello");
        assertEquals(helloPlusSpanish.getContent(esES), "hola");

        LocalizedString helloMinusEnglish = hello.without(enGB);

        final Locale currentLocale = I18N.getLocale();
        if (ptPT.equals(currentLocale)) {
            assertEquals(helloMinusEnglish.getContent(enGB), "olá");
        } else {
            assertNull(helloMinusEnglish.getContent(enGB));
        }
    }

    @Test
    public void testEquals() {
        LocalizedString hello1 = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();
        LocalizedString hello2 = new LocalizedString.Builder().with(enGB, "hello").with(ptPT, "olá").build();
        LocalizedString hello3 = new LocalizedString.Builder().with(enGB, "hello different").with(ptPT, "olá diferente").build();
        LocalizedString hello4 = hello1.builder().with(esES, "olá").build();

        assertEquals(hello1, hello2);
        assertNotEquals(hello1, hello3);
        assertNotEquals(hello1, hello4);
    }

    @Test
    public void testEmptyness() {
        LocalizedString hello = new LocalizedString(ptPT, "olá");
        LocalizedString empty = hello.without(ptPT);
        assertTrue(empty.isEmpty());
        assertTrue(new LocalizedString().isEmpty());
    }

    @Test
    public void testFallBacks() {
        LocalizedString hello1 = new LocalizedString(pt, "olá");
        assertEquals(hello1.getContent(ptPT), "olá");

        LocalizedString hello2 = new LocalizedString.Builder().with(ptPT, "olá").with(ptBR, "oi").build();
        assertTrue(hello2.getContent(pt).equals("olá") || hello2.getContent(pt).equals("oi"));

        LocalizedString hello3 = new LocalizedString.Builder().with(esES, "Hola").build();
        assertNull(hello3.getContent(pt));

        assertEquals(enGB, I18N.getLocale());
        LocalizedString hello4 = new LocalizedString.Builder().with(enUS, "Hello").build();
        assertEquals("Hello", hello4.getContent(enGB));
        // Should also return Hello, as the current locale (enGB) is compatible with enUS, even though we are requesting pt.
        assertEquals("Hello", hello4.getContent(pt));
    }

    @Test
    public void testGetContent() {
        assertEquals(enGB, I18N.getLocale());
        LocalizedString hello1 = new LocalizedString.Builder().with(esES, "Hola").with(enGB, "Hello").build();
        assertEquals("Hello", hello1.getContent());

        I18N.setLocale(ptPT);
        assertEquals(enGB, Locale.getDefault());
        // Even though we are in 'pt-PT', the default locale is 'en-GB'
        assertEquals("Hello", hello1.getContent());

        I18N.setLocale(esES);
        assertEquals("Hola", hello1.getContent());

        LocalizedString hello2 = new LocalizedString.Builder().with(esES, "Hola").build();
        assertEquals(esES, I18N.getLocale());
        assertEquals("Hola", hello2.getContent());

        I18N.setLocale(enGB);
        assertEquals("Hola", hello2.getContent());

        I18N.setLocale(null);
        assertEquals("Hola", hello2.getContent());

        assertNull(new LocalizedString().getContent());
    }

    @Test
    public void compareEmptyLocalizedString() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();
        assertTrue(new LocalizedString().compareTo(hello) < 0);
    }

    @Test
    public void compateWithEmptyLocalizedString() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();
        assertTrue(hello.compareTo(new LocalizedString()) > 0);
    }

    @Test
    public void compareEmptyLocalizedStrings() {
        assertEquals(0, new LocalizedString().compareTo(new LocalizedString()));
    }

    @Test
    public void appendShouldNotMixLangages() {
        LocalizedString hello = new LocalizedString.Builder().with(enGB, "Hello").build();
        LocalizedString world = new LocalizedString.Builder().with(enGB, "World").with(ptPT, "Mundo").build();
        LocalizedString helloworld = hello.append(world, " ");
        assertEquals("Hello World", helloworld.getContent(enGB));
        assertEquals("Mundo", helloworld.getContent(ptPT));
    }

    @Test
    public void testForEach() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();
        StringBuilder builder = new StringBuilder();

        hello.forEach((locale, value) -> builder.append(locale).append(value));

        assertTrue(builder.toString().equals("pt_PToláen_GBhello") || builder.toString().equals("en_GBhellopt_PTolá"));
    }

    @Test
    public void testAnyMatch() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();
        LocalizedString empty = new LocalizedString();

        assertEquals(true, hello.anyMatch(val -> val.contains("ll")));
        assertEquals(false, hello.anyMatch(val -> val.contains("xpto")));
        assertEquals(false, empty.anyMatch(val -> !val.isEmpty()));
    }

    @Test
    public void testGetOrDefault() {
        LocalizedString empty = new LocalizedString();
        assertEquals(null, empty.getContent(enGB));
        assertEquals("hello", empty.getOrDefault(enGB, "hello"));
    }

    @Test
    public void testMap() {
        LocalizedString hello = new LocalizedString.Builder().with(ptPT, "olá").with(enGB, "hello").build();
        LocalizedString helloUpper = new LocalizedString.Builder().with(ptPT, "OLÁ").with(enGB, "HELLO").build();

        assertEquals(helloUpper, hello.map(String::toUpperCase));
    }
}
