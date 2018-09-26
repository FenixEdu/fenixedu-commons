package org.fenixedu.commons.spreadsheet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SpreadsheetOldTest {

    public static class SomeBean {
        public BigDecimal getBigDecimal() {
            return new BigDecimal(new Random().nextDouble());
        }

        public DateTime getDateTime() {
            return new DateTime();
        }

        public Integer getInteger() {
            return new Random().nextInt();
        }

        public LocalDate getLocalDate() {
            return new LocalDate();
        }

        public Boolean getBoolean() {
            return new Random().nextBoolean();
        }

        public Double getDouble() {
            return new Random().nextDouble();
        }

        public String getString() {
            return "hello";
        }

        public Calendar getCalendar() {
            return Calendar.getInstance();
        }

        public Date getDate() {
            return new Date();
        }
    }

    @Test
    public void testSpreadsheet() throws IOException {
        new File("target/test-files/").mkdir();

        List<SomeBean> beans = new ArrayList<SomeBean>();
        for (int i = 0; i < 500; i++) {
            beans.add(new SomeBean());
        }

        SheetData<SomeBean> data = new SheetData<SomeBean>(beans) {
            @Override
            protected void makeLine(SomeBean item) {
                addCell(new String[] { "Number Stuff", "BigDecimal" }, new short[] { 3, 1 }, item.getBigDecimal(), (short) 1);
                addCell("Integer", item.getInteger());
                addCell("Double", item.getDouble());
                addCell(new String[] { "Date Stuff", "DateTime" }, new short[] { 4, 1 }, item.getDateTime(), (short) 1);
                addCell("LocalDate", item.getLocalDate());
                addCell("Calendar", item.getCalendar());
                addCell("Date", item.getDate());
                addCell(new String[] { "Other Stuff", "Boolean" }, new short[] { 2, 1 }, item.getBoolean(), (short) 1);
                addCell("String", item.getString());
            }
        };

        SheetData<SomeBean> data2 = new SheetData<SomeBean>(beans) {
            @Override
            protected void makeLine(SomeBean item) {
                addCell("Integer22", item.getInteger());
                addCell("Double", item.getDouble());
                addCell("LocalDate", item.getLocalDate());
                addCell("Calendar", item.getCalendar());
                addCell("Date", item.getDate());
                addCell(new String[] { "Other Stuff", "Boolean" }, new short[] { 2, 1 }, item.getBoolean(), (short) 1);
                addCell("String", item.getString());
            }
        };

        new SpreadsheetBuilder().addSheet("test", data).addSheet("test 2", data2).build(WorkbookExportFormat.DOCX,
                "target/test-files/test.xlsx");
        new SpreadsheetBuilder().addSheet("test", data).addSheet("test 2", data2).build(WorkbookExportFormat.EXCEL,
                "target/test-files/test.xls");
        new SpreadsheetBuilder().addSheet("test", data).build(WorkbookExportFormat.CSV, "target/test-files/test.csv");
    }
}
