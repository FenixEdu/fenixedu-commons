package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class CellFillPattern extends SpreadsheetCellStyle {

    private final FillPatternType pattern;

    public CellFillPattern(FillPatternType pattern) {
        this.pattern = pattern;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
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
