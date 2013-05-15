package pt.ist.dsi.commons.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

public class LocalizedStringTest {
    private static final Locale pt = Locale.forLanguageTag("pt");
    private static final Locale ptPT = Locale.forLanguageTag("pt-PT");
    private static final Locale enGB = Locale.forLanguageTag("en-GB");
    private static final Locale esES = Locale.forLanguageTag("es-ES");
    private static final Locale ptBR = Locale.forLanguageTag("pt-BR");

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
        assertNull(helloMinusEnglish.getContent(enGB));
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
        assertNull(helloMinusEnglish.getContent(enGB));
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
    }
}
