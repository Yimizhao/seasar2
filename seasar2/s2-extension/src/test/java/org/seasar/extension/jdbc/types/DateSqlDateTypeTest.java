/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.jdbc.types;

import java.sql.Date;
import java.util.Calendar;

import junit.framework.TestCase;

/**
 * @author taedium
 * 
 */
public class DateSqlDateTypeTest extends TestCase {

    /**
     * 
     * @throws Exception
     */
    public void testToSqlDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2007);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DATE, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 34);
        calendar.set(Calendar.SECOND, 56);
        calendar.set(Calendar.MILLISECOND, 789);
        Date date = new DateSqlDateType().toSqlDate(calendar.getTime());
        calendar.clear();
        calendar.setTime(date);
        assertEquals(2007, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.NOVEMBER, calendar.get(Calendar.MONTH));
        assertEquals(25, calendar.get(Calendar.DATE));
        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
    }
}
