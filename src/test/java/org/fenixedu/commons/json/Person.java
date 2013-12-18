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
package org.fenixedu.commons.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Person {

    protected String name;
    protected List<Contact> contacts;

    public Person(String name) {
        super();
        this.name = name;
        this.contacts = new ArrayList<>();
    }

    public void addContact(ContactType type, String value) {
        contacts.add(new Contact(type, value));
    }

    public Collection<Contact> getContacts() {
        return contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s %s", Person.class.getSimpleName(), name);
    }

}
