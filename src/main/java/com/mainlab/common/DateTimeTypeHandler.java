package com.mainlab.common;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

@MappedTypes(DateTime.class)
public class DateTimeTypeHandler implements TypeHandler {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        if (o != null) {
            preparedStatement.setTimestamp(i, new Timestamp(((DateTime) o).getMillis()));
        } else {
            preparedStatement.setTimestamp(i, null);
        }
    }

    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        Timestamp ts = resultSet.getTimestamp(s, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        if (ts != null) {
            // Stored DateTime in UTC in DB side
            return new DateTime(ts.getTime(), DateTimeZone.UTC);
        } else {
            return null;
        }
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        Timestamp ts = resultSet.getTimestamp(i, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        if (ts != null) {
            // Stored DateTime in UTC in DB side
            return new DateTime(ts.getTime(), DateTimeZone.UTC);
        } else {
            return null;
        }
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        Timestamp ts = callableStatement.getTimestamp(i, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        if (ts != null) {
            return new DateTime(ts.getTime(), DateTimeZone.UTC);
        } else {
            return null;
        }
    }
}
