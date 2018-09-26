package org.fenixedu.commons.spreadsheet.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class CellDateFormat extends SpreadsheetCellStyle {

    private String format = "dd/MM/yyyy hh:mm";

    public CellDateFormat() {
    }

    public CellDateFormat(String format) {
        this.format = format;
    }

    @Override
    protected void appendToStyle(Workbook book, CellStyle style, Font font) {
        CreationHelper helper = book.getCreationHelper();
        style.setDataFormat(helper.createDataFormat().getFormat(format));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellDateFormat) {
            CellDateFormat cellDataFormat = (CellDateFormat) obj;
            return format.equals(cellDataFormat.format);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return format.hashCode();
    }
}
