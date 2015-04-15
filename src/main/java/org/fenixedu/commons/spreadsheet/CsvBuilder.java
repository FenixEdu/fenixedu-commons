package org.fenixedu.commons.spreadsheet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fenixedu.commons.i18n.LocalizedString;
import org.fenixedu.commons.spreadsheet.SheetData.Cell;
import org.fenixedu.commons.spreadsheet.converters.CellConverter;
import org.fenixedu.commons.spreadsheet.converters.LocalizedStringCellConverter;
import org.fenixedu.commons.spreadsheet.converters.csv.BigDecimalCellConverter;
import org.fenixedu.commons.spreadsheet.converters.csv.CalendarCellConverter;
import org.fenixedu.commons.spreadsheet.converters.csv.DateCellConverter;
import org.fenixedu.commons.spreadsheet.converters.csv.DateTimeCellConverter;
import org.fenixedu.commons.spreadsheet.converters.csv.LocalDateCellConverter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

class CsvBuilder extends AbstractSheetBuilder {
    static Map<Class<?>, CellConverter> BASE_CONVERTERS;

    static {
        // TODO: grow this list to all common basic types.
        BASE_CONVERTERS = new HashMap<Class<?>, CellConverter>();
        BASE_CONVERTERS.put(DateTime.class, new DateTimeCellConverter());
        BASE_CONVERTERS.put(LocalDate.class, new LocalDateCellConverter());
        BASE_CONVERTERS.put(GregorianCalendar.class, new CalendarCellConverter());
        BASE_CONVERTERS.put(Date.class, new DateCellConverter());
        BASE_CONVERTERS.put(BigDecimal.class, new BigDecimalCellConverter());
        BASE_CONVERTERS.put(LocalizedString.class, new LocalizedStringCellConverter());
    }

    {
        converters.putAll(BASE_CONVERTERS);
    }

    public void build(Map<String, SheetData<?>> sheets, OutputStream output, String separator) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        try {
            List<String> lines = new ArrayList<String>();
            for (SheetData<?> data : sheets.values()) {
                if (!data.headers.get(0).isEmpty()) {
                    for (List<Cell> headerRow : data.headers) {
                        List<String> column = new ArrayList<String>();
                        for (Cell cell : headerRow) {
                            column.add(cell.value.toString());
                            if (cell.span > 1) {
                                column.addAll(Arrays.asList(new String[cell.span - 1]));
                            }
                        }
                        lines.add(String.join(separator, column));
                    }
                }
                for (final List<Cell> line : data.matrix) {
                    List<String> column = new ArrayList<String>();
                    for (Cell cell : line) {
                        column.add(cell.value != null ? convert(cell.value).toString().replace(separator, " ") : "");
                        if (cell.span > 1) {
                            column.addAll(Arrays.asList(new String[cell.span - 1]));
                        }
                    }
                    lines.add(String.join(separator, column));
                }
            }
            writer.write(String.join("\n", lines));
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
