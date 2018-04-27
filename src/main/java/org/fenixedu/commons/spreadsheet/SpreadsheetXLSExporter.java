package org.fenixedu.commons.spreadsheet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadsheetXLSExporter {

    public void exportToXLSXSheet(final Spreadsheet spreadsheet, final OutputStream outputStream) throws IOException {
       exportToExcelSheet(XSSFWorkbook::new, spreadsheet, outputStream);
    }

    public void exportToXLSSheet(final Spreadsheet spreadsheet, final OutputStream outputStream) throws IOException {
        exportToExcelSheet(XSSFWorkbook::new, spreadsheet, outputStream);
    }

    private void exportToExcelSheet(final Supplier<Workbook> provider, final Spreadsheet spreadsheet, final OutputStream outputStream) throws IOException {
        final Workbook workbook = provider.get();
        final ExcelStyle excelStyle = new ExcelStyle(workbook);
        exportToXLSSheet(workbook, spreadsheet, excelStyle.getHeaderStyle(), excelStyle.getStringStyle());
        workbook.write(outputStream);
    }

    public void exportToXLSSheet(final Spreadsheet spreadsheet, final File file) throws IOException {
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            exportToXLSSheet(spreadsheet, outputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void exportToXLSSheets(final OutputStream outputStream, final Spreadsheet... spreadsheets) throws IOException {
        final Workbook workbook = new HSSFWorkbook();
        final ExcelStyle excelStyle = new ExcelStyle(workbook);
        for (final Spreadsheet spreadsheet : spreadsheets) {
            exportToXLSSheet(workbook, spreadsheet, excelStyle.getHeaderStyle(), excelStyle.getStringStyle());
        }
        workbook.write(outputStream);
    }

    public void exportToXLSSheets(final OutputStream outputStream, final List<Spreadsheet> spreadsheets) throws IOException {
        final Workbook workbook = new HSSFWorkbook();
        final ExcelStyle excelStyle = new ExcelStyle(workbook);
        for (final Spreadsheet spreadsheet : spreadsheets) {
            exportToXLSSheet(workbook, spreadsheet, excelStyle.getHeaderStyle(), excelStyle.getStringStyle());
        }
        workbook.write(outputStream);
    }

    public void exportToXLSSheet(Workbook workbook, Spreadsheet spreadsheet, CellStyle headerCellStyle,
                                 CellStyle cellStyle) {

        final Sheet sheet = workbook.createSheet(spreadsheet.getName());
        sheet.setDefaultColumnWidth(20);

        exportXLSHeaderLine(sheet, headerCellStyle, spreadsheet.getHeader());

        for (final Spreadsheet.Row row : spreadsheet.getRows()) {
            exportXLSRowLine(sheet, cellStyle, row.getCells());
        }

        final Spreadsheet next = spreadsheet.getNextSpreadsheet();
        if (next != null) {
            exportToXLSSheet(workbook, next, headerCellStyle, cellStyle);
        }
    }

    protected void exportXLSHeaderLine(final Sheet sheet, final CellStyle cellStyle, final List<Object> cells) {
        exportXLSLine(sheet, cellStyle, cells, 0);
    }

    protected void exportXLSRowLine(final Sheet sheet, final CellStyle cellStyle, final List<Object> cells) {
        exportXLSLine(sheet, cellStyle, cells, 1);
    }

    protected void exportXLSLine(final Sheet sheet, final CellStyle cellStyle, final List<Object> cells, final int offset) {
        final Row row = sheet.createRow(sheet.getLastRowNum() + offset);
        for (final Object cellValue : cells) {
            addColumn(cellStyle, row, cellValue);
        }
    }

    protected Cell addColumn(final CellStyle cellStyle, final Row row, final Object cellValue) {
        final Cell cell = row.createCell(row.getLastCellNum() == -1 ? 0 : (int) row.getLastCellNum());
        cell.setCellStyle(cellStyle);
        if (cellValue != null) {
            cell.setCellValue(cellValue.toString());
        } else {
            cell.setCellValue("");
        }
        return cell;
    }

}
