package org.fenixedu.commons.spreadsheet.converters.csv;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;
import org.joda.time.DateTime;

public class DateTimeCellConverter implements CellConverter {
    @Override
    public Object convert(Object source) {
        return (source != null) ? ((DateTime) source).toString("dd/MM/yyyy hh:mm") : null;
    }
}
