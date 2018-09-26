package org.fenixedu.commons.spreadsheet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;

import org.fenixedu.commons.spreadsheet.AbstractSheetBuilder;
import org.fenixedu.commons.spreadsheet.SheetData.Cell;
import org.fenixedu.commons.spreadsheet.SheetData;
import org.fenixedu.commons.spreadsheet.converters.matcher.ConverterMatcher;
import org.fenixedu.commons.spreadsheet.converters.matcher.DefaultConverterMatcher;

public abstract class AbstractSheetBuilder {
    protected OutputStream outputStream;
    private boolean initialized = false;
    protected SheetData<?> currentSheet;
    protected ConverterMatcher convertedMatcher = new DefaultConverterMatcher();
    protected final Map<Class<?>, CellConverter> converters = new LinkedHashMap<Class<?>, CellConverter>();
    protected int rownum;
    protected int usefulAreaStart;
    protected int usefulAreaEnd;
    protected String name;

    protected Object convert(Object content) {
        return convertedMatcher.convert(converters, content);
    }

    protected void addConverter(Class<?> type, CellConverter converter) {
        converters.put(type, converter);
    }

    @SuppressWarnings("unchecked")
    protected <SB extends AbstractSheetBuilder> SB converterMatcher(ConverterMatcher convertedMatcher) {
        this.convertedMatcher = convertedMatcher;
        return (SB) this;
    }

    protected AbstractSheetBuilder(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private final void initStream() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    private void initSheet(SheetData<?> sheet) {
        if (sheet == null) {
            throw new IllegalArgumentException();
        }
        if (currentSheet == null) {
            doInitSheet(sheet);
        } else {
            boolean newSheet = currentSheet != sheet;
            if (newSheet) {
                doInitSheet(sheet);
            }
        }
    }

    protected void doInitSheet(SheetData<?> sheet) {
        currentSheet = sheet;
        rownum = 0;
    }

    protected void init() {
    }

    public void addHeaders(SheetData<?> sheet, List<List<Cell>> headers) {
        initStream();
        initSheet(sheet);
        doAddHeaders(headers);
        usefulAreaStart = headers.size();
    }

    protected abstract void doAddHeaders(List<List<Cell>> headers);

    public void addRow(SheetData<?> sheet, List<Cell> row) {
        initStream();
        initSheet(sheet);
        doAddRow(row);
    }

    protected abstract void doAddRow(List<Cell> row);

    public void addFooter(SheetData<?> sheet) {
    }

    public void write(String name, SheetData<?> data) {
        this.initialized = false;
        this.name = name;
        data.write(this);
    }

    public void close() {
        try {
            outputStream.flush();
//            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

