package org.fenixedu.commons.json;

import com.google.gson.JsonElement;

public interface JsonCreator<T> {
    T create(JsonElement json, JsonBuilder ctx);
}
