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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.seasar.extension.jdbc.ValueType;
import org.seasar.framework.util.CalendarConversionUtil;

/**
 * {@link Calendar} 用の {@link ValueType}です。
 * 
 * @author taedium
 * 
 */
public class CalendarType implements ValueType {

    private ValueType valueType;

    /**
     * インスタンスを構築します。
     * 
     * @param valueType
     *            委譲先の{@link ValueType}
     */
    public CalendarType(ValueType valueType) {
        this.valueType = valueType;
    }

    public void bindValue(CallableStatement cs, String parameterName,
            Object value) throws SQLException {
        valueType.bindValue(cs, parameterName, value);

    }

    public void bindValue(PreparedStatement ps, int index, Object value)
            throws SQLException {
        valueType.bindValue(ps, index, value);

    }

    public Object getValue(CallableStatement cs, int index) throws SQLException {
        return CalendarConversionUtil.toCalendar(valueType.getValue(cs, index));
    }

    public Object getValue(CallableStatement cs, String parameterName)
            throws SQLException {
        return CalendarConversionUtil.toCalendar(valueType.getValue(cs,
                parameterName));
    }

    public Object getValue(ResultSet resultSet, int index) throws SQLException {
        return CalendarConversionUtil.toCalendar(valueType.getValue(resultSet,
                index));
    }

    public Object getValue(ResultSet resultSet, String columnName)
            throws SQLException {
        return CalendarConversionUtil.toCalendar(valueType.getValue(resultSet,
                columnName));
    }

    public void registerOutParameter(CallableStatement cs, int index)
            throws SQLException {
        valueType.registerOutParameter(cs, index);

    }

    public void registerOutParameter(CallableStatement cs, String parameterName)
            throws SQLException {
        valueType.registerOutParameter(cs, parameterName);
    }

}