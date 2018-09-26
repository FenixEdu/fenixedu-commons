package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class CellVerticalAlignment extends SpreadsheetCellStyle {

    private final VerticalAlignment align;

    public CellVerticalAlignment(VerticalAlignment align) {
        this.align = align;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        style.setVerticalAlignment(align);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellVerticalAlignment) {
            CellVerticalAlignment cellVerticalAlignment = (CellVerticalAlignment) obj;
            return align == cellVerticalAlignment.align;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return align.hashCode();
    }
}
