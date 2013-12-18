/**
 * Copyright (c) 2013, Instituto Superior Técnico. All rights reserved.
 *
 * This file is part of fenixedu-commons.
 *
 * fenixedu-commons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fenixedu-commons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fenixedu-commons. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.commons.i18n;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stores a {@link Locale} in a thread local variable, all locale based functions should access locale from here.
 */
public class I18N {
    private static final Logger logger = LoggerFactory.getLogger(I18N.class);

    private static final String LOCALE_KEY = I18N.class.getName() + "_LOCAL_KEY";

    private static final InheritableThreadLocal<Locale> locale = new InheritableThreadLocal<>();

    /**
     * Gets the {@link Locale} for this thread. The locale returned is the first hit in the following chain:
     * <ul>
     * <li>A manual, session scoped, setting</li>
     * <li>The user preferred language</li>
     * <li>The java-vm default locale</li>
     * </ul>
     * 
     * @return The {@link Locale} instance, never null
     */
    public static Locale getLocale() {
        return locale.get() != null ? locale.get() : Locale.getDefault();
    }

    /**
     * Sets the {@link Locale} for current thread, and for current session (if a session is passed)
     * 
     * @param session Option session instance,
     * @param locale Locale to set
     */
    public static void setLocale(HttpSession session, Locale locale) {
        if (session != null) {
            session.setAttribute(LOCALE_KEY, locale);
        }
        setLocale(locale);
    }

    /**
     * Sets the {@link Locale} for the current thread only.
     * 
     * @param locale Locale to set
     */
    public static void setLocale(Locale locale) {
        I18N.locale.set(locale);
        logger.trace("Set locale to: {}", locale);
    }

    public static void updateFromSession(HttpSession session) {
        if (session != null && session.getAttribute(LOCALE_KEY) != null) {
            locale.set((Locale) session.getAttribute(LOCALE_KEY));
            logger.trace("Set thread's locale to: {}", locale.get().toString());
        } else {
            locale.set(null);
        }
    }
}
