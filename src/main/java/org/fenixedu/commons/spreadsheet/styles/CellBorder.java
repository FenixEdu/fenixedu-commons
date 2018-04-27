package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;

import com.google.common.base.Objects;

public class CellBorder extends CellStyle {

    private final BorderStyle borderBottom;
    private final BorderStyle borderLeft;
    private final BorderStyle borderRight;
    private final BorderStyle borderTop;

    public CellBorder(BorderStyle border) {
        this.borderBottom = border;
        this.borderLeft = border;
        this.borderRight = border;
        this.borderTop = border;
    }

    @Override
    protected void appendToStyle(HSSFWorkbook book, HSSFCellStyle style, HSSFFont font) {
        style.setBorderBottom(borderBottom);
        style.setBorderLeft(borderLeft);
        style.setBorderRight(borderRight);
        style.setBorderTop(borderTop);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellBorder) {
            CellBorder cellBorder = (CellBorder) obj;
            return borderBottom == cellBorder.borderBottom && borderTop == cellBorder.borderTop
                    && borderLeft == cellBorder.borderLeft && borderRight == cellBorder.borderRight;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(borderBottom, borderLeft, borderRight, borderTop);
    }
}
