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

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JsonBuilder {
    private final Map<Class<?>, Object> adapters = new ConcurrentHashMap<>();
    private final TableRegistry<JsonCreator> creators = new TableRegistry<>(adapters);
    private final TableRegistry<JsonUpdater> updaters = new TableRegistry<>(adapters);
    private final TableRegistry<JsonViewer> viewers = new TableRegistry<>(adapters);

    public void setDefault(Class<?> objectClass, Class<?> registeeClass) {
        if (JsonCreator.class.isAssignableFrom(registeeClass)) {
            creators.setDefault(objectClass, (Class<? extends JsonCreator>) registeeClass);
        }
        if (JsonViewer.class.isAssignableFrom(registeeClass)) {
            viewers.setDefault(objectClass, (Class<? extends JsonViewer>) registeeClass);
        }
        if (JsonUpdater.class.isAssignableFrom(registeeClass)) {
            updaters.setDefault(objectClass, (Class<? extends JsonUpdater>) registeeClass);
        }
    }

    public JsonElement view(Object obj) {
        return view(obj, null);
    }

    public JsonElement view(Object obj, Class<? extends JsonViewer> jsonViewerClass) {
        return view(obj, obj == null ? null : obj.getClass(), jsonViewerClass);
    }

    public JsonElement view(Object obj, Class<?> objectClass, Class<? extends JsonViewer> jsonViewerClass) {
        if (objectClass == null) {
            return null;
        }

        if (Collection.class.isAssignableFrom(objectClass)) {
            return views((Collection) obj, jsonViewerClass);
        }

        return viewers.get(objectClass, jsonViewerClass).view(obj, this);
    }

    private JsonArray views(Collection coll, Class<? extends JsonViewer> jsonViewerClass) {
        JsonArray json = new JsonArray();
        for (final Object el : coll) {
            json.add(view(el, jsonViewerClass));
        }
        return json;
    }

    public <T> T create(JsonElement json, Class<T> clazz) {
        return create(json, clazz, null);
    }

    public <T> T create(JsonElement json, Class<T> clazz, Class<? extends JsonCreator> jsonCreatorClass) {
        final JsonCreator jsonCreator = creators.get(clazz, jsonCreatorClass);
        return (T) jsonCreator.create(json, this);
    }

    public <T> T update(JsonElement json, T object) {
        return update(json, object, null);
    }

    public <T> T update(JsonElement json, T object, Class<? extends JsonUpdater> jsonUpdaterClass) {
        final JsonUpdater jsonUpdater = updaters.get(object.getClass(), jsonUpdaterClass);
        return (T) jsonUpdater.update(json, object, this);
    }
}
