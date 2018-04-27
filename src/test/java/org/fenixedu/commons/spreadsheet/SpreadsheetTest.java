package org.fenixedu.commons.spreadsheet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.IntStream;

import org.fenixedu.commons.spreadsheet.Spreadsheet.Row;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Sérgio Silva (hello@fenixedu.org).
 */
@RunWith(JUnit4.class)
public class SpreadsheetTest {

    @Test
    public void testSpreadsheet() {
        Spreadsheet spreadsheet = new Spreadsheet("test");
        IntStream.range(0, 70000).forEach(i -> {
            Row row = spreadsheet.addRow();
            row.setCell("index", i);
        });
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            spreadsheet.exportToXLSXSheet(outputStream);
        } catch (IOException e) {
            Assert.fail("failed with exception: " + e.getMessage());
        }
    }
}
