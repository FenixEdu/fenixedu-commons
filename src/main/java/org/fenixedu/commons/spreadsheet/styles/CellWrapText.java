package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class CellWrapText extends SpreadsheetCellStyle {

    private final boolean wrap;

    public CellWrapText(boolean wrap) {
        this.wrap = wrap;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        style.setWrapText(wrap);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellWrapText) {
            CellWrapText cellWrapText = (CellWrapText) obj;
            return wrap == cellWrapText.wrap;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return wrap ? 1 : 0;
    }
}
