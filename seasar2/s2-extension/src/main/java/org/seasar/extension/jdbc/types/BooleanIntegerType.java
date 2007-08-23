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
import java.sql.Types;

import org.seasar.extension.jdbc.ValueType;
import org.seasar.framework.util.BooleanConversionUtil;

/**
 * Boolean用の {@link ValueType}です。
 * 
 * @author higa
 * 
 */
public class BooleanIntegerType extends AbstractValueType {

    /**
     * インスタンスを構築します。
     */
    public BooleanIntegerType() {
        super(Types.INTEGER);
    }

    public Object getValue(ResultSet resultSet, int index) throws SQLException {
        return BooleanConversionUtil.toBoolean(resultSet.getObject(index));
    }

    public Object getValue(ResultSet resultSet, String columnName)
            throws SQLException {

        return BooleanConversionUtil.toBoolean(resultSet.getObject(columnName));
    }

    public Object getValue(CallableStatement cs, int index) throws SQLException {
        return BooleanConversionUtil.toBoolean(cs.getObject(index));
    }

    public Object getValue(CallableStatement cs, String parameterName)
            throws SQLException {

        return BooleanConversionUtil.toBoolean(cs.getObject(parameterName));
    }

    public void bindValue(PreparedStatement ps, int index, Object value)
            throws SQLException {
        if (value == null) {
            setNull(ps, index);
        } else {
            ps.setInt(index,
                    BooleanConversionUtil.toPrimitiveBoolean(value) ? 1 : 0);
        }
    }
}