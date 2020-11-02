package org.fenixedu.commons.spreadsheet.converters.matcher;

import java.util.Map;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;

public class DefaultConverterMatcher implements ConverterMatcher {

    @Override
    public Object convert(Map<Class<?>, CellConverter> converters, Object content) {
        /*
        if (converters.containsKey(content.getClass())) {
            CellConverter converter = converters.get(content.getClass());
            return converter.convert(content);
        }
        return content;
         */

        if (content != null) {
            for (Class<?> clazz : converters.keySet()) {
                if (clazz.isAssignableFrom(content.getClass())) {
                    CellConverter converter = converters.get(clazz);
                    return converter.convert(content);
                }
            }
        }
        return content;
    }
}
