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
package org.fenixedu.commons.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;

public class ConfigurationInvocationHandler extends AbstractInvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationInvocationHandler.class);

    public static final String NULL_DEFAULT = "AbQAGOvdWgQgHLOH5hSk";

    protected static final Properties properties = new Properties();

    private static final Map<Class<?>, Object> configs = new HashMap<>();

    static {
        String propertiesFile = System.getProperty("CONFIGURATION_PROPERTIES", "/configuration.properties");
        try (InputStream inputStream = ConfigurationInvocationHandler.class.getResourceAsStream(propertiesFile)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                logger.warn("{} not found in classpath. Relying on default values", propertiesFile);
            }
        } catch (IOException e) {
            throw new Error(propertiesFile + "could not be read.", e);
        }
    }

    private final Map<String, Object> cache = new HashMap<>();

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if (cache.containsKey(name)) {
            return cache.get(name);
        }
        Class<?> type = method.getReturnType();
        ConfigurationProperty property = method.getAnnotation(ConfigurationProperty.class);
        if (property != null) {
            if (property.key().contains("*")) {
                Map<String, String> value = obtainValueMap(property.key(), property.defaultValue());
                logger.debug("Setting {} to {}", name, value);
                cache.put(name, value);
                return value;
            }
            Object value = obtainValue(type, property.key(), property.defaultValue());
            logger.debug("Setting {} to {}", name, value);
            cache.put(name, value);
            return value;
        }
        throw new Error("Method " + name + "must be annotated with @ConfigurationProperty");
    }

    private Object obtainValue(Class<?> type, String key, String defaultValue) {
        Object value = properties.getProperty(key, defaultValue.equals(NULL_DEFAULT) ? null : defaultValue);
        if (value == null) {
            return null;
        }
        if (Boolean.class.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type)) {
            value = Boolean.parseBoolean(((String) value).trim());
        } else if (Byte.class.isAssignableFrom(type) || Byte.TYPE.isAssignableFrom(type)) {
            value = Byte.parseByte(((String) value).trim());
        } else if (Short.class.isAssignableFrom(type) || Short.TYPE.isAssignableFrom(type)) {
            value = Short.parseShort(((String) value).trim());
        } else if (Integer.class.isAssignableFrom(type) || Integer.TYPE.isAssignableFrom(type)) {
            value = Integer.parseInt(((String) value).trim());
        } else if (Long.class.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type)) {
            value = Long.parseLong(((String) value).trim());
        } else if (Float.class.isAssignableFrom(type) || Float.TYPE.isAssignableFrom(type)) {
            value = Float.parseFloat(((String) value).trim());
        } else if (Double.class.isAssignableFrom(type) || Double.TYPE.isAssignableFrom(type)) {
            value = Double.parseDouble(((String) value).trim());
        }
        return value;
    }

    private Map<String, String> obtainValueMap(String patternString, String defaultValue) {
        Map<String, String> value = new HashMap<>();
        Pattern pattern = Pattern.compile(patternString.replace("*", "(.*)"));
        for (Object property : properties.keySet()) {
            String propertyName = property.toString();
            Matcher matcher = pattern.matcher(propertyName);
            if (matcher.matches()) {
                String key = matcher.group(1);
                value.put(key, properties.getProperty(propertyName, defaultValue.equals(NULL_DEFAULT) ? null : defaultValue));
            }
        }
        return value;
    }

    public static Properties rawProperties() {
        return (Properties) properties.clone();
    }

    /**
     * Creates an instance of the class annotated with {@link ConfigurationManager} that resolves properties to the
     * configuration.properties file.
     * 
     * @param propertiesType class annotated with {@link ConfigurationManager}.
     * @return A proxy implementation of the given type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getConfiguration(Class<T> propertiesType) {
        if (!configs.containsKey(propertiesType)) {
            configs.put(propertiesType, Reflection.newProxy(propertiesType, new ConfigurationInvocationHandler()));
        }
        return (T) configs.get(propertiesType);
    }
}
