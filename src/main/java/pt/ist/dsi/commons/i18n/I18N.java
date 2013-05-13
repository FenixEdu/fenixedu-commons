/*
 * I18N.java
 *
 * Copyright (c) 2013, Instituto Superior Técnico. All rights reserved.
 *
 * This file is part of dsi-commons.
 *
 * dsi-commons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * dsi-commons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with dsi-commons.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.dsi.commons.i18n;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18N {
    private static final Logger logger = LoggerFactory.getLogger(I18N.class);

    private static final InheritableThreadLocal<Locale> locale = new InheritableThreadLocal<>();

    /**
     * Gets the {@link Locale} for this thread.
     * 
     * @return The {@link Locale} instance, never null
     */
    public static Locale getLocale() {
        return locale.get() != null ? locale.get() : Locale.getDefault();
    }

    /**
     * Sets the thread's {@link Locale}
     * 
     * @param locale
     */
    public static void setLocale(Locale locale) {
        I18N.locale.set(locale);
        logger.debug("Set locale to: {}", locale);
    }
}
