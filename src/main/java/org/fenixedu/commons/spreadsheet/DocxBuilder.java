package org.fenixedu.commons.spreadsheet;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.fenixedu.commons.spreadsheet.converters.CellConverter;
import org.fenixedu.commons.spreadsheet.converters.excel.BigDecimalCellConverter;
import org.fenixedu.commons.spreadsheet.converters.excel.DateTimeCellConverter;
import org.fenixedu.commons.spreadsheet.converters.excel.IntegerCellConverter;
import org.fenixedu.commons.spreadsheet.converters.excel.LocalDateCellConverter;
import org.fenixedu.commons.spreadsheet.styles.CellAlignment;
import org.fenixedu.commons.spreadsheet.styles.CellBorder;
import org.fenixedu.commons.spreadsheet.styles.CellDateFormat;
import org.fenixedu.commons.spreadsheet.styles.CellFillForegroundColor;
import org.fenixedu.commons.spreadsheet.styles.CellFillPattern;
import org.fenixedu.commons.spreadsheet.styles.CellVerticalAlignment;
import org.fenixedu.commons.spreadsheet.styles.CellWrapText;
import org.fenixedu.commons.spreadsheet.styles.ComposedCellStyle;
import org.fenixedu.commons.spreadsheet.styles.FontBold;
import org.fenixedu.commons.spreadsheet.styles.FontColor;
import org.fenixedu.commons.spreadsheet.styles.FontHeight;
import org.fenixedu.commons.spreadsheet.styles.SpreadsheetCellStyle;
import org.fenixedu.commons.spreadsheet.styles.StyleCache;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

class DocxBuilder extends AbstractSheetBuilder {
    protected SXSSFWorkbook book;

    public DocxBuilder(OutputStream outputStream) {
        super(outputStream);
        this.book = new SXSSFWorkbook();
        this.book.setCompressTempFiles(true);
        this.styleCache = new StyleCache(this.book);
    }

    static Map<Class<?>, CellConverter> BASE_CONVERTERS;

    static {
        // TODO: grow this list to all common basic types.
        BASE_CONVERTERS = new HashMap<Class<?>, CellConverter>();
        BASE_CONVERTERS.put(Integer.class, new IntegerCellConverter());
        BASE_CONVERTERS.put(DateTime.class, new DateTimeCellConverter());
        BASE_CONVERTERS.put(LocalDate.class, new LocalDateCellConverter());
        BASE_CONVERTERS.put(BigDecimal.class, new BigDecimalCellConverter());
    }

    private static Map<Class<?>, SpreadsheetCellStyle> TYPE_STYLES;

    static {
        TYPE_STYLES = new HashMap<Class<?>, SpreadsheetCellStyle>();
        TYPE_STYLES.put(DateTime.class, new CellDateFormat());
        TYPE_STYLES.put(YearMonthDay.class, new CellDateFormat("dd/MM/yyyy"));
        TYPE_STYLES.put(LocalDate.class, new CellDateFormat("dd/MM/yyyy"));
        TYPE_STYLES.put(GregorianCalendar.class, new CellDateFormat());
        TYPE_STYLES.put(Date.class, new CellDateFormat());
    }

    private static List<SpreadsheetCellStyle> ROW_STYLES = Collections.emptyList();

    private static SpreadsheetCellStyle HEADER_STYLE = new ComposedCellStyle() {
        {
            IndexedColors black = IndexedColors.BLACK;
            IndexedColors gray = IndexedColors.GREY_25_PERCENT;

            merge(new FontColor(black));
            merge(new FontBold());
            merge(new FontHeight((short) 8));
            merge(new CellAlignment(HorizontalAlignment.CENTER));
            merge(new CellFillForegroundColor(gray));
            merge(new CellFillPattern(FillPatternType.SOLID_FOREGROUND));
            merge(new CellBorder(BorderStyle.THIN));
            merge(new CellVerticalAlignment(VerticalAlignment.CENTER));
            merge(new CellWrapText(true));
        }
    };

    {
        converters.putAll(BASE_CONVERTERS);
    }

    private SpreadsheetCellStyle headerStyle = HEADER_STYLE;

    private final Map<Class<?>, SpreadsheetCellStyle> typeStyles = new HashMap<Class<?>, SpreadsheetCellStyle>(TYPE_STYLES);

    private List<SpreadsheetCellStyle> rowStyles = new ArrayList<SpreadsheetCellStyle>(ROW_STYLES);

    private StyleCache styleCache;

    int usefulAreaStart;

    int usefulAreaEnd;

    private int colnum;

    private Sheet sheet;

    protected void setHeaderStyle(SpreadsheetCellStyle style) {
        headerStyle = style;
    }

    protected void appendHeaderStyle(SpreadsheetCellStyle style) {
        ComposedCellStyle composed = new ComposedCellStyle();
        composed.merge(headerStyle);
        composed.merge(style);
        headerStyle = composed;
    }

    protected void addTypeStyle(Class<?> type, SpreadsheetCellStyle style) {
        typeStyles.put(type, style);
    }

    protected void setRowStyle(SpreadsheetCellStyle... styles) {
        rowStyles = Arrays.asList(styles);
    }

    protected void setValue(Workbook book, Cell cell, Object value, short span) {
        ComposedCellStyle style = new ComposedCellStyle();
        if (!rowStyles.isEmpty()) {
            style.merge(rowStyles.get(cell.getRowIndex() % rowStyles.size()));
        }
        if (value != null && typeStyles.containsKey(value.getClass())) {
            style.merge(typeStyles.get(value.getClass()));
        }
        setValue(book, cell, value, span, styleCache.getStyle(style));
    }

    private void setValue(Workbook book, Cell cell, Object value, short span, CellStyle style) {
        if (value != null) {
            Object content = convert(value);
            if (content instanceof Boolean) {
                cell.setCellValue((Boolean) content);
            } else if (content instanceof Double) {
                cell.setCellValue((Double) content);
            } else if (content instanceof String) {
                cell.setCellValue((String) content);
            } else if (content instanceof GregorianCalendar) {
                cell.setCellValue((GregorianCalendar) content);
            } else if (content instanceof Date) {
                cell.setCellValue((Date) content);
            } else if (content instanceof RichTextString) {
                cell.setCellValue((RichTextString) content);
            } else {
                cell.setCellValue(content.toString());
            }
        } else {
            // cell.setCellValue((String) null);
            // NullPointerException when using 'SXSSFWorkbook' and 'autoSizeColumns' on 'getCellWidth' method.
            cell.setCellValue((String) "");
        }
        if (span > 1) {
            CellRangeAddress region = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(),
                    cell.getColumnIndex() + span - 1);
            cell.getSheet().addMergedRegion(region);
        }
        cell.setCellStyle(style);
    }

    @Override
    protected void doInitSheet(SheetData<?> sheet) {
        super.doInitSheet(sheet);
        colnum = 0;
        this.sheet = book.createSheet(name);
    }

    @Override
    protected void doAddHeaders(List<List<org.fenixedu.commons.spreadsheet.SheetData.Cell>> headers) {
        if (!headers.get(0).isEmpty()) {
            for (List<org.fenixedu.commons.spreadsheet.SheetData.Cell> headerRow : headers) {
                colnum = 0;
                final Row row = this.sheet.createRow(rownum++);
                for (org.fenixedu.commons.spreadsheet.SheetData.Cell cell : headerRow) {
                    setValue(book, row.createCell(colnum++), cell.getValue(), cell.getSpan(), styleCache.getStyle(headerStyle));
                    colnum = colnum + cell.getSpan() - 1;
                }
            }
        }
    }

    @Override
    protected void doAddRow(List<org.fenixedu.commons.spreadsheet.SheetData.Cell> rowCells) {
        colnum = 0;
        final Row row = this.sheet.createRow(rownum++);
        for (org.fenixedu.commons.spreadsheet.SheetData.Cell cell : rowCells) {
            setValue(book, row.createCell(colnum++), cell.getValue(), cell.getSpan());
            colnum = colnum + cell.getSpan() - 1;
        }
    }

    @Override
    public void addFooter(SheetData<?> sheetData) {
        colnum = 0;
        final Row row = this.sheet.createRow(rownum++);
        for (org.fenixedu.commons.spreadsheet.SheetData.Cell cell : sheetData.getFooter()) {
            setValue(book, row.createCell(colnum++), cell.getValue(), cell.getSpan());
            colnum = colnum + cell.getSpan() - 1;
        }
    }

    public void close() {
        try {
            book.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        book.dispose();
        super.close();
    }

    @Override
    public void write(String name, SheetData<?> data) {
        super.write(name, data);
        int headersSize = this.currentSheet.getHeaders().size();
        this.sheet.createFreezePane(0, headersSize);
        if (rownum < 10000 && colnum < 100) {
            if (sheet instanceof SXSSFSheet) {
                ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
            }
            for (int i = 0; i < colnum; ++i) {
                sheet.autoSizeColumn(i);
            }
        }
    }

}
