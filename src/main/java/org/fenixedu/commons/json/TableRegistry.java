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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

class TableRegistry<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableRegistry.class);

    private final Table<Class<?>, Class<? extends T>, T> wrapped;
    private final Map<Class<?>, Class<? extends T>> defaultValues;
    private final Map<Class<?>, Object> adapters;

    public TableRegistry(Map<Class<?>, Object> adapters) {
        wrapped = HashBasedTable.create();
        defaultValues = new ConcurrentHashMap<>();
        this.adapters = adapters;
    }

    private T getOrCreateRecord(Class<? extends T> recordClass) {
        if (adapters.containsKey(recordClass)) {
            return (T) adapters.get(recordClass);
        }
        try {
            final T record = recordClass.newInstance();
            adapters.put(recordClass, record);
            return record;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can't create instance for type " + recordClass.getName(), e);
        }
    }

    private synchronized T register(Class<?> objectClass, Class<? extends T> recordClass) {
        //if (!defaultValues.containsKey(objectClass)) {
        //    defaultValues.put(objectClass, recordClass);
        //}

        if (wrapped.contains(objectClass, recordClass)) {
            return wrapped.get(objectClass, recordClass);
        }

        return register(objectClass, recordClass, getOrCreateRecord(recordClass));
    }

    private synchronized T register(Class<?> objectClass, Class<? extends T> recordClass, T record) {
        LOGGER.trace("record : objectClass {} recordClass {} record {}", objectClass, recordClass, record);
        wrapped.put(objectClass, recordClass, record);
        return record;
    }

    public T setDefault(Class<?> objectClass, Class<? extends T> recordClass) {
        LOGGER.trace("setDefault : objectClass {} recordClass {}", objectClass, recordClass);

        defaultValues.put(objectClass, recordClass);

        if (!wrapped.contains(objectClass, recordClass)) {
            register(objectClass, recordClass);
        }

        return wrapped.get(objectClass, recordClass);
    }

    private Class<? extends T> internalGet(Class<?> objectClass) {
        if (objectClass == null) {
            return null;
        }
        if (defaultValues.containsKey(objectClass)) {
            return defaultValues.get(objectClass);
        }
        return internalGet(objectClass.getSuperclass());
    }

    private T get(Class<?> objectClass) {
        final Class<? extends T> defaultValue = internalGet(objectClass);
        if (defaultValue == null) {
            throw new DefaultNotAvailableException(objectClass);
        }
        return get(objectClass, defaultValue);
    }

    public T get(Class<?> objectClass, Class<? extends T> recordClass) {
        if (recordClass == null) {
            return get(objectClass);
        }
        return register(objectClass, recordClass);
    }

    public T lookup(Type objectClass, Type recordClass) {
        return wrapped.get(objectClass, recordClass);
    }

}
