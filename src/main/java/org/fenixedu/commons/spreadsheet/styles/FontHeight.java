package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class FontHeight extends SpreadsheetCellStyle {

    private final short height;

    public FontHeight(short height) {
        this.height = height;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        font.setFontHeightInPoints(height);
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
        if (obj instanceof FontHeight) {
            FontHeight fontHeight = (FontHeight) obj;
            return height == fontHeight.height;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return height;
    }
}
