package org.fenixedu.commons.spreadsheet.converters.csv;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;
import org.joda.time.LocalDate;

public class LocalDateCellConverter implements CellConverter {
    @Override
    public Object convert(Object source) {
        return (source != null) ? ((LocalDate) source).toString("dd/MM/yyyy") : null;
    }
}
