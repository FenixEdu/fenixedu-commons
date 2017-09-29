package org.fenixedu.commons.spreadsheet.converters.excel;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;
import org.joda.time.DateTime;

public class DateTimeCellConverter implements CellConverter {
    @Override
    public Object convert(Object source) {
        return (source != null) ? ((DateTime) source).toDate() : null;
    }
}
