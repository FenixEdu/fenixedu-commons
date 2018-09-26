package org.fenixedu.commons.spreadsheet.styles;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class StyleCache {
    private Workbook book;

    private Map<SpreadsheetCellStyle, CellStyle> cache = new HashMap<SpreadsheetCellStyle, CellStyle>();

    public StyleCache(Workbook book) {
        this.book = book;
    }

    public CellStyle getStyle(SpreadsheetCellStyle style) {
        if (!cache.containsKey(style)) {
            cache.put(style, style.getStyle(book));
        }
        return cache.get(style);
    }

    public int getSize() {
        return cache.size();
    }
}
