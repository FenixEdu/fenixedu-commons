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

public class PersonViewer implements JsonViewer<Person> {

    @Override
    public JsonElement view(Person obj, JsonBuilder ctx) {
        JsonObject json = new JsonObject();
        json.addProperty("name", obj.getName());
        if (!obj.getContacts().isEmpty()) {
            json.add("contacts", ctx.view(obj.getContacts()));
        }
        return json;
    }

}
