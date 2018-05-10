package org.fenixedu.commons.spreadsheet.converters.csv;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.fenixedu.commons.spreadsheet.converters.CellConverter;

public class BigDecimalCellConverter implements CellConverter {

    private int scale;
    private RoundingMode mode;
    private boolean custom = false;

    public BigDecimalCellConverter() {
    }

    public BigDecimalCellConverter(final int scale, final RoundingMode mode) {
        this.custom = true;
        this.scale = scale;
        this.mode = mode;
    }

    @Override
    public Object convert(Object source) {
        if (source != null) {
            final BigDecimal value = (BigDecimal) source;
            return custom ? value.setScale(scale, mode).toString() : value.toString();
        }
        return null;
    }
}
