package org.fenixedu.commons.spreadsheet.converters;

import java.util.Locale;

import org.fenixedu.commons.i18n.LocalizedString;

public class LocalizedStringCellConverter implements CellConverter {

    private Locale locale = null;

    public LocalizedStringCellConverter() {
    }

    public LocalizedStringCellConverter(final Locale locale) {
        this.locale = locale;
    }

    @Override
    public Object convert(Object source) {

        if (source != null) {
            final LocalizedString value = (LocalizedString) source;
            return (locale != null) ? value.getContent(locale) : value.getContent();
        }

        return null;
    }

}
