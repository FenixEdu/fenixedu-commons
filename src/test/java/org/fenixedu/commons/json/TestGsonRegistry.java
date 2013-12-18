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
package org.fenixedu.commons.json;

import java.lang.reflect.Type;

import org.junit.BeforeClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

class Serializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", src.getName());
        return obj;
    }

}

class OtherSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", src.getName());
        obj.addProperty("cenas", "outras cenas");
        return obj;
    }

}

//@RunWith(JUnit4.class)
public class TestGsonRegistry {

    private static GsonBuilder builder;
    private static Person user;

    @BeforeClass
    public static void setup() {
        builder = new GsonBuilder();
        user = new User("test", "testpwd", "1");
        builder.registerTypeAdapter(User.class, new Serializer());
        builder.registerTypeAdapter(User.class, new OtherSerializer());
    }

    //@Test
    public void testGson() {
        final Gson create = builder.create();
    }
}
