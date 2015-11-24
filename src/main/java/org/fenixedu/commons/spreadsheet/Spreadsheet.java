/*
 * Created on Jan 24, 2006
 *	by mrsp & lepc
 */
package org.fenixedu.commons.spreadsheet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Use new approach to excel table generation: {@link SpreadsheetBuilder}.
 */
public class Spreadsheet {

    public class Row {
        private final List<Object> cells = new ArrayList<Object>();

        protected Row() {
        }

        public Row setCell(final int columnIndex, final String cellValue) {
            for (int i = cells.size(); i < (columnIndex + 1); i++) {
                cells.add("");
            }
            cells.set(columnIndex, cellValue);
            return this;
        }

        public Row setCell(final String cellValue) {
            cells.add(cellValue);
            return this;
        }

        public Row setCell(final Integer cellValue) {
            cells.add((cellValue != null) ? cellValue.toString() : "");
            return this;
        }

        public Row setCell(final Double cellValue) {
            cells.add((cellValue != null) ? cellValue.toString() : "");
            return this;
        }

        public Row setCell(final BigDecimal cellValue) {
            cells.add((cellValue != null) ? cellValue.toPlainString() : "");
            return this;
        }

        public Row setCell(final Boolean cellValue) {
            cells.add((cellValue != null) ? cellValue.toString() : "");
            return this;
        }

        public Row setCell(final String header, final String cellValue) {
            setCell(getHeaderIndex(header), cellValue);
            return this;
        }

        public Row setCell(final String header, final Integer cellValue) {
            setCell(getHeaderIndex(header), (cellValue != null) ? cellValue.toString() : "");
            return this;
        }

        public Row setCell(final String header, final Double cellValue) {
            setCell(getHeaderIndex(header), (cellValue != null) ? cellValue.toString() : "");
            return this;
        }

        public Row setCell(final String header, final BigDecimal cellValue) {
            setCell(getHeaderIndex(header), (cellValue != null) ? cellValue.toPlainString() : "");
            return this;
        }

        public Row setValues(final String[] values) {
            for (int i = 0; i < values.length; i++) {
                setCell(i, values[i]);
            }
            return this;
        }

        public List<Object> getCells() {
            return Collections.unmodifiableList(cells);
        }
    }

    private String name;

    private final List<Object> headers;

    private List<Row> rows = new ArrayList<Row>();

    private Spreadsheet next = null;

    public Spreadsheet(final String name) {
        this(name, new ArrayList<Object>());
    }

    public Spreadsheet(final String name, final List<Object> header) {
        setName(name);
        this.headers = header;
    }

    public Spreadsheet addSpreadsheet(final String name) {
        return next = new Spreadsheet(name);
    }

    public Spreadsheet addSpreadsheet(final String name, final List<Object> header) {
        return next = new Spreadsheet(name, header);
    }

    public Spreadsheet getNextSpreadsheet() {
        return next;
    }

    private int getHeaderIndex(final String header) {
        int i = 0;
        for (final Object o : headers) {
            if (header.equals(o)) {
                return i;
            }
            i++;
        }
        setHeader(header);
        return i;
    }

    protected String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name.substring(0, Math.min(31, name.length())).replace('(', '_').replace(')', '_');
    }

    protected List<Object> getHeader() {
        return headers;
    }

    public Spreadsheet setHeader(final int columnNumber, final String columnHeader) {
        for (int i = headers.size(); i < columnNumber; i++) {
            headers.add("");
        }
        headers.add(columnNumber, columnHeader);
        return this;
    }

    public Spreadsheet setHeader(final String columnHeader) {
        headers.add(columnHeader);
        return this;
    }

    public Spreadsheet setHeaders(final String... headers) {
        for (int i = 0; i < headers.length; i++) {
            setHeader(i, headers[i]);
        }
        return this;
    }

    public Row addRow(final int rowNumber) {
        for (int i = rows.size(); i < rowNumber; i++) {
            rows.add(new Row());
        }
        return addRow();
    }

    public Row addRow() {
        final Row row = new Row();
        rows.add(row);
        return row;
    }

    public Row getRow(final int rowNumber) {
        return rows.get(rowNumber);
    }

    public List<Row> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public void exportToCSV(final OutputStream outputStream, final String columnSeperator) throws IOException {
        exportToCSV(outputStream, columnSeperator, "\n");
    }

    public void exportToCSV(final OutputStream outputStream, final String columnSeperator, final String lineSepeator)
            throws IOException {
        exportCSVLine(outputStream, columnSeperator, lineSepeator, headers);
        for (final Row row : rows) {
            exportCSVLine(outputStream, columnSeperator, lineSepeator, row.getCells());
        }
    }

    public void exportToCSV(final File file, final String columnSeperator) throws IOException {
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            exportToCSV(outputStream, columnSeperator);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void exportToCSV(final File file, final String columnSeperator, final String lineSepeator) throws IOException {
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            exportToCSV(outputStream, columnSeperator, lineSepeator);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void exportCSVLine(final OutputStream outputStream, final String columnSeperator, final String lineSepeator,
            final List<Object> cells) throws IOException {
        final byte[] columnSeperatorAsBytes = columnSeperator.getBytes();

        for (int i = 0; i < cells.size(); i++) {
            final Object cellValue = cells.get(i);

            if (i > 0) {
                outputStream.write(columnSeperatorAsBytes);
            }

            if (cellValue == null) {
                outputStream.write("".getBytes());
            } else {
                outputStream.write(cellValue.toString().replace(columnSeperator, "").getBytes());
            }
        }
        outputStream.write(lineSepeator.getBytes());
    }

    public void exportToXLSSheet(final OutputStream outputStream) throws IOException {
        new SpreadsheetXLSExporter().exportToXLSSheet(this, outputStream);
    }

    public void exportToXLSSheet(final File file) throws IOException {
        new SpreadsheetXLSExporter().exportToXLSSheet(this, file);
    }

    public void exportToXLSSheet(final HSSFWorkbook workbook, final HSSFCellStyle headerCellStyle, final HSSFCellStyle cellStyle) {
        new SpreadsheetXLSExporter().exportToXLSSheet(workbook, this, headerCellStyle, cellStyle);
    }

    public static void exportToXLSSheets(final OutputStream outputStream, List<Spreadsheet> spreadsheets) throws IOException {
        new SpreadsheetXLSExporter().exportToXLSSheets(outputStream, spreadsheets);
    }

}
