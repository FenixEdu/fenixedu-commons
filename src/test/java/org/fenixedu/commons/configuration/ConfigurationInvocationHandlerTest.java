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
package org.fenixedu.commons.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConfigurationInvocationHandlerTest {

    @ConfigurationManager
    public static interface TestConfiguration {

        @ConfigurationProperty(key = "test.primitive.boolean", defaultValue = "true")
        public boolean primitiveBoolean();

        @ConfigurationProperty(key = "test.primitive.byte", defaultValue = "3")
        public byte primitiveByte();

        @ConfigurationProperty(key = "test.primitive.short", defaultValue = "5")
        public short primitiveShort();

        @ConfigurationProperty(key = "test.primitive.int", defaultValue = "-1")
        public int primitiveInt();

        @ConfigurationProperty(key = "test.primitive.long", defaultValue = "9")
        public long primitiveLong();

        @ConfigurationProperty(key = "test.primitive.float", defaultValue = "1.1")
        public float primitiveFloat();

        @ConfigurationProperty(key = "test.primitive.double", defaultValue = "7.5")
        public double primitiveDouble();

    }

    @Test
    public void testPrimitiveTypes() {
        TestConfiguration config = ConfigurationInvocationHandler.getConfiguration(TestConfiguration.class);

        Assert.assertEquals(true, config.primitiveBoolean());
        Assert.assertEquals(3, config.primitiveByte());
        Assert.assertEquals(5, config.primitiveShort());
        Assert.assertEquals(-1, config.primitiveInt());
        Assert.assertEquals(9, config.primitiveLong());
        Assert.assertEquals(1.1, config.primitiveFloat(), 0.1f);
        Assert.assertEquals(7.5d, config.primitiveDouble(), 0.1d);
    }

}
