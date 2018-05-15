package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;

public class CellFillPattern extends CellStyle {

    private final FillPatternType pattern;

    public CellFillPattern(FillPatternType pattern) {
        this.pattern = pattern;
    }

    @Override
    protected void appendToStyle(HSSFWorkbook book, HSSFCellStyle style, HSSFFont font) {
        style.setFillPattern(pattern);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellFillPattern) {
            CellFillPattern cellFillPattern = (CellFillPattern) obj;
            return pattern == cellFillPattern.pattern;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return pattern.hashCode();
    }
}
