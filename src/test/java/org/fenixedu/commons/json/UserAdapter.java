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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UserAdapter implements JsonAdapter<User> {

    @Override
    public User create(JsonElement jsonElement, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        final String name = jsonObject.get("name").getAsString();
        final String password = jsonObject.get("password").getAsString();
        final String number = jsonObject.get("number").getAsString();
        return new User(name, password, number);
    }

    @Override
    public User update(JsonElement jsonElement, User obj, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        final String name = jsonObject.get("name").getAsString();
        final String password = jsonObject.get("password").getAsString();
        final String number = jsonObject.get("number").getAsString();
        obj.setName(name);
        obj.setPassword(password);
        obj.setNumber(number);
        return obj;
    }

    @Override
    public JsonElement view(User obj, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", obj.getName());
        jsonObject.addProperty("password", obj.getPassword());
        jsonObject.addProperty("number", obj.getNumber());
        return jsonObject;
    }
}
