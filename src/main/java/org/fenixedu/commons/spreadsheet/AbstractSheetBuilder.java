package org.fenixedu.commons.spreadsheet;

import java.util.HashMap;
import java.util.Map;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;

class AbstractSheetBuilder {
    protected final Map<Class<?>, CellConverter> converters = new HashMap<Class<?>, CellConverter>();

    protected Object convert(Object content) {
        if (converters.containsKey(content.getClass())) {
            CellConverter converter = converters.get(content.getClass());
            return converter.convert(content);
        }
        return content;
    }

    protected void addConverter(Class<?> type, CellConverter converter) {
        converters.put(type, converter);
    }
}
