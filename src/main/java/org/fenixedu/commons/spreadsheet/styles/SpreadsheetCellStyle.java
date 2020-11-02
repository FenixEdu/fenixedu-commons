package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class SpreadsheetCellStyle {
    public CellStyle getStyle(Workbook book) {
        CellStyle style = book.createCellStyle();
        appendToStyle(book, style, null);
        return style;
    }

    protected abstract void appendToStyle(Workbook book, CellStyle style, Font font);
}
