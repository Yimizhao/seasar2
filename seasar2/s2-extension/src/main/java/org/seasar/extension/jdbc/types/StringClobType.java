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

import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.seasar.extension.jdbc.ValueType;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.StringConversionUtil;

/**
 * ClobをStringとして扱うための {@link ValueType}です。
 * 
 * @author manhole
 */
public class StringClobType extends AbstractValueType {

    /**
     * インスタンスを構築します。
     */
    public StringClobType() {
        super(Types.CLOB);
    }

    public Object getValue(ResultSet resultSet, int index) throws SQLException {
        return convertToString(resultSet.getCharacterStream(index));
    }

    public Object getValue(ResultSet resultSet, String columnName)
            throws SQLException {
        return convertToString(resultSet.getCharacterStream(columnName));
    }

    public Object getValue(CallableStatement cs, int index) throws SQLException {
        return convertToString(cs.getClob(index));
    }

    public Object getValue(CallableStatement cs, String parameterName)
            throws SQLException {
        return convertToString(cs.getClob(parameterName));
    }

    private String convertToString(Reader reader) throws SQLException {
        if (reader == null) {
            return null;
        }
        return ReaderUtil.readText(reader);
    }

    private String convertToString(Clob clob) throws SQLException {
        if (clob == null) {
            return null;
        }
        return convertToString(clob.getCharacterStream());
    }

    public void bindValue(PreparedStatement ps, int index, Object value)
            throws SQLException {
        if (value == null) {
            ps.setNull(index, Types.CLOB);
        } else {
            final String s = StringConversionUtil.toString(value);
            ps.setCharacterStream(index, new StringReader(s), s.length());
        }
    }
}