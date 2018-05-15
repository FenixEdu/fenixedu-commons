package org.fenixedu.commons.spreadsheet;

import java.io.Serializable;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelStyle implements Serializable {

    private static final long serialVersionUID = 6778686809629990612L;

    private CellStyle titleStyle;

    private CellStyle headerStyle;

    private CellStyle verticalHeaderStyle;

    private CellStyle stringStyle;

    private CellStyle doubleStyle;

    private CellStyle doubleNegativeStyle;

    private CellStyle integerStyle;

    private CellStyle labelStyle;

    private CellStyle valueStyle;

    private CellStyle redValueStyle;

    public ExcelStyle(Workbook wb) {
        setTitleStyle(wb);
        setHeaderStyle(wb);
        setVerticalHeaderStyle(wb);
        setStringStyle(wb);
        setDoubleStyle(wb);
        setDoubleNegativeStyle(wb);
        setIntegerStyle(wb);
        setLabelStyle(wb);
        setValueStyle(wb);
        setRedValueStyle(wb);
    }

    private void setTitleStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        titleStyle = style;
    }

    private void setHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        headerStyle = style;
    }

    private void setVerticalHeaderStyle(Workbook wb) {
        verticalHeaderStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        verticalHeaderStyle.setFont(font);
        verticalHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
        verticalHeaderStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        verticalHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        verticalHeaderStyle.setBorderLeft(BorderStyle.THIN);
        verticalHeaderStyle.setBorderRight(BorderStyle.THIN);
        verticalHeaderStyle.setBorderBottom(BorderStyle.THIN);
        verticalHeaderStyle.setBorderTop(BorderStyle.THIN);
        verticalHeaderStyle.setRotation((short) 90);
    }

    private void setStringStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        stringStyle = style;
    }

    private void setDoubleStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
        doubleStyle = style;
    }

    private void setDoubleNegativeStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
        font.setColor(HSSFColor.RED.index);
        doubleNegativeStyle = style;
    }

    private void setIntegerStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setDataFormat(wb.createDataFormat().getFormat("0"));
        integerStyle = style;
    }

    private void setLabelStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        labelStyle = style;
    }

    private void setValueStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        valueStyle = style;
    }

    private void setRedValueStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        redValueStyle = style;
    }

    public CellStyle getDoubleNegativeStyle() {
        return doubleNegativeStyle;
    }

    public CellStyle getDoubleStyle() {
        return doubleStyle;
    }

    public CellStyle getHeaderStyle() {
        return headerStyle;
    }

    public CellStyle getIntegerStyle() {
        return integerStyle;
    }

    public CellStyle getLabelStyle() {
        return labelStyle;
    }

    public CellStyle getStringStyle() {
        return stringStyle;
    }

    public CellStyle getTitleStyle() {
        return titleStyle;
    }

    public CellStyle getValueStyle() {
        return valueStyle;
    }

    public CellStyle getRedValueStyle() {
        return redValueStyle;
    }

    public CellStyle getVerticalHeaderStyle() {
        return verticalHeaderStyle;
    }
}
