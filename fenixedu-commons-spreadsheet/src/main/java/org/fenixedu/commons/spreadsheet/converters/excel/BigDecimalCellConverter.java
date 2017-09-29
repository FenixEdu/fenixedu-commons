package org.fenixedu.commons.spreadsheet.converters.excel;

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
    public Object convert(final Object source) {
        return source == null ? null : new Double(scale((BigDecimal) source));
    }

    private double scale(final BigDecimal value) {
        return custom ? value.setScale(scale, mode).doubleValue() : value.doubleValue();
    }
}
