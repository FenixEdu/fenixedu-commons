package org.fenixedu.commons.spreadsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;
import org.fenixedu.commons.spreadsheet.styles.SpreadsheetCellStyle;

/**
 * Builder for all kinds of Spreadsheets (currently supports excel, csv, and
 * tsv). Basically, given a format, and a set of {@link SheetData}s it
 * constructs a spreadsheet in the specified {@link OutputStream}.
 *
 * It can be further customized with {@link CellConverter}s, and {@link SpreadsheetCellStyle}s (that are only useful in the excel
 * format). A
 * startup set
 * of converters and styles were already created, if you need more consider
 * extending these sets in the inner classes instead of just passing as a custom
 * one. If its not domain specific it should be in the tools jar.
 *
 * @author Pedro Santos (pedro.miguel.santos@ist.utl.pt)
 */
public class SpreadsheetBuilder {
    private Map<String, SheetData<?>> sheets = new LinkedHashMap<String, SheetData<?>>();
    private final Map<Class<?>, CellConverter> converters = new LinkedHashMap<Class<?>, CellConverter>();
    private SpreadsheetCellStyle headerStyle = null;
    private SpreadsheetCellStyle mergeHeaderStyle = null;
    private final Map<Class<?>, SpreadsheetCellStyle> typeStyles = new HashMap<Class<?>, SpreadsheetCellStyle>();
    private List<SpreadsheetCellStyle> rowStyles = new ArrayList<SpreadsheetCellStyle>();

    public SpreadsheetBuilder() {
    }

    /**
     * Adds a sheet to the resulting work. You need at least one to have
     * something useful.
     *
     * @param name
     *            The name of the sheet.
     * @param sheet
     *            the sheet data.
     * @return this.
     */
    public SpreadsheetBuilder addSheet(String name, SheetData<?> sheet) {
        sheets.put(name, sheet);
        return this;
    }

    /**
     * Adds a custom type converter.
     *
     * @param type
     *            The type of object to be converted
     * @param converter
     *            The converter class
     * @return this.
     */
    public SpreadsheetBuilder addConverter(Class<?> type, CellConverter converter) {
        converters.put(type, converter);
        return this;
    }

    /**
     * Overrides the header style.
     *
     * @param style
     *            The style specification
     * @return this.
     */
    public SpreadsheetBuilder setHeaderStyle(SpreadsheetCellStyle style) {
        headerStyle = style;
        return this;
    }

    /**
     * Merges the specified style the the existing header style.
     *
     * @param style
     *            The style specification
     * @return this.
     */
    protected SpreadsheetBuilder appendHeaderStyle(SpreadsheetCellStyle style) {
        mergeHeaderStyle = style;
        return this;
    }

    /**
     * Adds a new style by object type.
     *
     * @param type
     *            The type of object (before conversion) on which all cells of
     *            that object have the specified style applied.
     * @param style
     *            The style specification
     * @return this.
     */
    protected SpreadsheetBuilder addTypeStyle(Class<?> type, SpreadsheetCellStyle style) {
        typeStyles.put(type, style);
        return this;
    }

    /**
     * Adds a set of row styles. If more than one is specified they are applied
     * alternated on the lines, this can be used to achieve that grey/white line
     * background alternation.
     *
     * @param styles
     *            A set of style specifications
     * @return this.
     */
    protected SpreadsheetBuilder setRowStyle(SpreadsheetCellStyle... styles) {
        rowStyles = Arrays.asList(styles);
        return this;
    }

    /**
     * Writes the data sets in the specified file.
     *
     * @param format
     *            type of spreadsheet
     * @param filename
     *            the output file
     * @throws IOException
     *             if and error occurs while writing.
     */
    public void build(WorkbookExportFormat format, String filename) throws IOException {
        build(format, new File(filename));
    }

    /**
     * Writes the data sets in the specified file.
     *
     * @param format
     *            type of spreadsheet
     * @param file
     *            the output file
     * @throws IOException
     *             if and error occurs while writing.
     */
    public void build(WorkbookExportFormat format, File file) throws IOException {
        build(format, new FileOutputStream(file));
    }

    /**
     * Writes the data sets in the specified stream.
     *
     * @param format
     *            type of spreadsheet
     * @param output
     *            the output stream
     * @throws IOException
     *             if and error occurs while writing.
     */
    public void build(WorkbookExportFormat format, OutputStream output) throws IOException {
        AbstractSheetBuilder sb = null;
        switch (format) {
        case EXCEL: {
            ExcelBuilder builder = new ExcelBuilder(output);
            sb = builder;
            if (headerStyle != null) {
                builder.setHeaderStyle(headerStyle);
            }
            if (mergeHeaderStyle != null) {
                builder.appendHeaderStyle(mergeHeaderStyle);
            }
            for (Entry<Class<?>, SpreadsheetCellStyle> entry : typeStyles.entrySet()) {
                builder.addTypeStyle(entry.getKey(), entry.getValue());
            }
            builder.setRowStyle(rowStyles.toArray(new SpreadsheetCellStyle[0]));
            break;
        }
        case CSV:
        case TSV: {
            String textDelimiter = "";
            CsvBuilder builder = new CsvBuilder(output, format.getSeparator(), textDelimiter);
            sb = builder;
            break;
        }
        case DOCX:
            DocxBuilder builder = new DocxBuilder(output);
            sb = builder;
            if (headerStyle != null) {
                builder.setHeaderStyle(headerStyle);
            }
            if (mergeHeaderStyle != null) {
                builder.appendHeaderStyle(mergeHeaderStyle);
            }
            for (Entry<Class<?>, SpreadsheetCellStyle> entry : typeStyles.entrySet()) {
                builder.addTypeStyle(entry.getKey(), entry.getValue());
            }
            builder.setRowStyle(rowStyles.toArray(new SpreadsheetCellStyle[0]));
            break;
        }
        try {
            addConverter(sb);
            writeSheets(sb);
        } finally {
            sb.close();
        }
    }

    private void writeSheets(AbstractSheetBuilder builder) {
        for (Entry<String, SheetData<?>> entry : sheets.entrySet()) {
            SheetData<?> data = entry.getValue();
            String name = entry.getKey();
            builder.write(name, data);
        }
    }

    private void addConverter(AbstractSheetBuilder builder) {
        for (Entry<Class<?>, CellConverter> entry : converters.entrySet()) {
            builder.addConverter(entry.getKey(), entry.getValue());
        }
    }
}
