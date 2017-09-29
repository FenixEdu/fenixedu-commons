package org.fenixedu.commons.stream;

import static org.junit.Assert.assertEquals;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@RunWith(JUnit4.class)
public class StreamUtilsTest {

    @Test
    public void testEmptyArray() {
        JsonArray out = Stream.<JsonObject> of().collect(StreamUtils.toJsonArray());
        assertEquals(0, out.size());
    }

    @Test
    public void testPrimitivesArray() {
        JsonArray out =
                Stream.of(new JsonPrimitive("Hello"), new JsonObject(), new JsonArray()).collect(StreamUtils.toJsonArray());
        assertEquals(3, out.size());
    }

    @Test
    public void testStreamFromJsonArray() {
        JsonArray array = new JsonArray();
        array.add(new JsonPrimitive("hello"));
        array.add(new JsonPrimitive("world"));

        assertEquals(array, StreamUtils.of(array).collect(StreamUtils.toJsonArray()));
    }
}
