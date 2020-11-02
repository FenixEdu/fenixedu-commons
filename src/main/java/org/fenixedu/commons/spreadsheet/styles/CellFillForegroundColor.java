package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class CellFillForegroundColor extends SpreadsheetCellStyle {

    private final IndexedColors color;

    public CellFillForegroundColor(IndexedColors color) {
        this.color = color;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        style.setFillForegroundColor(color.getIndex());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellFillForegroundColor) {
            CellFillForegroundColor cellFillForegroundColor = (CellFillForegroundColor) obj;
            return color.equals(cellFillForegroundColor.color);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }
}
