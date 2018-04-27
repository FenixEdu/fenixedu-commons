package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class CellAlignment extends CellStyle {

    private final HorizontalAlignment align;

    public CellAlignment(HorizontalAlignment align) {
        this.align = align;
    }

    @Override
    protected void appendToStyle(HSSFWorkbook book, HSSFCellStyle style, HSSFFont font) {
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
