package org.fenixedu.commons.spreadsheet.converters.matcher;

import java.util.Map;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;

public interface ConverterMatcher {

    Object convert(Map<Class<?>, CellConverter> converters, Object content);

}
