package org.fenixedu.commons.spreadsheet.converters.excel;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;
import org.joda.time.LocalDate;

public class LocalDateCellConverter implements CellConverter {
    @Override
    public Object convert(Object source) {
        return (source != null) ? ((LocalDate) source).toDateTimeAtStartOfDay().toDate() : null;
    }
}
