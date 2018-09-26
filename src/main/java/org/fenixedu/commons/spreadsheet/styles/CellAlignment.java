package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class CellAlignment extends SpreadsheetCellStyle {

    private final HorizontalAlignment align;

    public CellAlignment(HorizontalAlignment align) {
        this.align = align;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        style.setAlignment(align);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellAlignment) {
            CellAlignment cellAlignment = (CellAlignment) obj;
            return cellAlignment.align == align;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return align.hashCode();
    }
}
