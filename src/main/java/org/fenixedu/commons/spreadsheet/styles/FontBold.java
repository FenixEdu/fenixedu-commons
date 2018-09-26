package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class FontBold extends SpreadsheetCellStyle {

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        font.setBold(true);
    }

    @Override
    public CellStyle getStyle(Workbook book) {
        CellStyle style = book.createCellStyle();
        Font font = book.createFont();
        appendToStyle(book, style, font);
        style.setFont(font);
        return style;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FontBold;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
