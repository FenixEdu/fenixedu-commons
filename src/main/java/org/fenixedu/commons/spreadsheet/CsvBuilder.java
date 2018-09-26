package org.fenixedu.commons.spreadsheet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.joda.time.YearMonthDay;

import org.fenixedu.commons.spreadsheet.converters.csv.YearMonthDayCellConverter;

class CsvBuilder extends AbstractSheetBuilder {
    private static final String NL = System.getProperty("line.separator");
    private String separator;
    private String textDelimiter;
    private OutputStreamWriter writer;

    public CsvBuilder(OutputStream outputStream, String separator, String textDelimiter) {
        super(outputStream);
        this.separator = separator;
        this.textDelimiter = textDelimiter;
    }

    static Map<Class<?>, CellConverter> BASE_CONVERTERS;

    static {
        // TODO: grow this list to all common basic types.
        BASE_CONVERTERS = new HashMap<Class<?>, CellConverter>();
        BASE_CONVERTERS.put(DateTime.class, new DateTimeCellConverter());
        BASE_CONVERTERS.put(YearMonthDay.class, new YearMonthDayCellConverter());
        BASE_CONVERTERS.put(LocalDate.class, new LocalDateCellConverter());
        BASE_CONVERTERS.put(GregorianCalendar.class, new CalendarCellConverter());
        BASE_CONVERTERS.put(Date.class, new DateCellConverter());
        BASE_CONVERTERS.put(BigDecimal.class, new BigDecimalCellConverter());
        BASE_CONVERTERS.put(LocalizedString.class, new LocalizedStringCellConverter());
    }

    {
        converters.putAll(BASE_CONVERTERS);
    }

    @Override
    protected Object convert(Object content) {
        Object value = super.convert(content);
        if (value != null) {
            return value.toString().replaceAll("[\t\n]+", "");
        }
        return value;
    }

    @Override
    protected void init() {
        super.init();
        try {
            writer = new OutputStreamWriter(outputStream, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doAddHeaders(List<List<Cell>> headers) {
        if (!headers.iterator().next().isEmpty()) {
            for (List<Cell> headerRow : headers) {
                List<Object> r = new ArrayList<Object>();
                for (Cell cell : headerRow) {
                    r.add(convert(cell.getValue()).toString());
                    if (cell.getSpan() > 1) {
                        r.addAll(Arrays.asList(new String[cell.getSpan() - 1]));
                    }
                }
                rownum++;
                write(r);
            }
        }
    }

    protected void write(List<Object> row) {
        String line = row.stream().map(c -> c != null ? c.toString() : "").collect(Collectors.joining(separator));
        try {
            writer.write(line);
            writer.write(NL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doAddRow(List<Cell> lines) {
        List<Object> column = new ArrayList<Object>();
        for (Cell cell : lines) {
            StringBuilder sb = new StringBuilder();
            sb.append(textDelimiter);
            if (cell.getValue() != null) {
                sb.append(convert(cell.getValue()));
            }
            sb.append(textDelimiter);
            column.add(sb.toString());
            if (cell.getSpan() > 1) {
                column.addAll(Arrays.asList(new String[cell.getSpan() - 1]));
            }
        }
        write(column);
    }

    public void close() {
        if (writer != null) { // prevent NullPointerException when some error on first row.
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        super.close();
    }

}
