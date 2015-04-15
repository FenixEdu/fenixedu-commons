package org.fenixedu.commons.spreadsheet;

import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelStyle implements Serializable {

    private static final long serialVersionUID = 6778686809629990612L;

    private HSSFCellStyle titleStyle;

    private HSSFCellStyle headerStyle;

    private HSSFCellStyle verticalHeaderStyle;

    private HSSFCellStyle stringStyle;

    private HSSFCellStyle doubleStyle;

    private HSSFCellStyle doubleNegativeStyle;

    private HSSFCellStyle integerStyle;

    private HSSFCellStyle labelStyle;

    private HSSFCellStyle valueStyle;

    private HSSFCellStyle redValueStyle;

    public ExcelStyle(HSSFWorkbook wb) {
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

    private void setTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle = style;
    }

    private void setHeaderStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        headerStyle = style;
    }

    private void setVerticalHeaderStyle(HSSFWorkbook wb) {
        verticalHeaderStyle = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 8);
        verticalHeaderStyle.setFont(font);
        verticalHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        verticalHeaderStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        verticalHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        verticalHeaderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        verticalHeaderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        verticalHeaderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        verticalHeaderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        verticalHeaderStyle.setRotation((short) 90);
    }

    private void setStringStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        stringStyle = style;
    }

    private void setDoubleStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
        doubleStyle = style;
    }

    private void setDoubleNegativeStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
        font.setColor(HSSFColor.RED.index);
        doubleNegativeStyle = style;
    }

    private void setIntegerStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setDataFormat(wb.createDataFormat().getFormat("0"));
        integerStyle = style;
    }

    private void setLabelStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        labelStyle = style;
    }

    private void setValueStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        valueStyle = style;
    }

    private void setRedValueStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        font.setFontHeightInPoints((short) 8);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        redValueStyle = style;
    }

    public HSSFCellStyle getDoubleNegativeStyle() {
        return doubleNegativeStyle;
    }

    public HSSFCellStyle getDoubleStyle() {
        return doubleStyle;
    }

    public HSSFCellStyle getHeaderStyle() {
        return headerStyle;
    }

    public HSSFCellStyle getIntegerStyle() {
        return integerStyle;
    }

    public HSSFCellStyle getLabelStyle() {
        return labelStyle;
    }

    public HSSFCellStyle getStringStyle() {
        return stringStyle;
    }

    public HSSFCellStyle getTitleStyle() {
        return titleStyle;
    }

    public HSSFCellStyle getValueStyle() {
        return valueStyle;
    }

    public HSSFCellStyle getRedValueStyle() {
        return redValueStyle;
    }

    public HSSFCellStyle getVerticalHeaderStyle() {
        return verticalHeaderStyle;
    }
}
