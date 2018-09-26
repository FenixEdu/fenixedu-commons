package org.fenixedu.commons.spreadsheet.styles;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class ComposedCellStyle extends SpreadsheetCellStyle {
    private final List<SpreadsheetCellStyle> parts = new ArrayList<SpreadsheetCellStyle>();

    @Override
    public CellStyle getStyle(Workbook book) {
        CellStyle style = book.createCellStyle();
        Font font = book.createFont();
        for (SpreadsheetCellStyle part : parts) {
            part.appendToStyle(book, style, font);
        }
        style.setFont(font);
        return style;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        // Nothing to do.
    }

    public SpreadsheetCellStyle merge(SpreadsheetCellStyle style) {
        parts.add(style);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComposedCellStyle) {
            ComposedCellStyle composedCellStyle = (ComposedCellStyle) obj;
            boolean equals = true;
            for (int i = 0; i < parts.size(); i++) {
                if (!parts.get(i).equals(composedCellStyle.parts.get(i))) {
                    equals = false;
                    break;
                }
            }
            return equals;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (SpreadsheetCellStyle part : parts) {
            result += part.hashCode();
        }
        return result;
    }
}
