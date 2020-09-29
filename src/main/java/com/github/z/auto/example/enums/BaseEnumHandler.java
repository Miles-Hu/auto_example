package com.github.z.auto.example.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hujun
 * @date 2019-08-16 20:22
 */
public class BaseEnumHandler<T extends BaseEnum> extends BaseTypeHandler<T> {

    private T[] enums;

    public BaseEnumHandler(Class<T> type){
        if (type == null){
            throw new UnsupportedOperationException("Type cannot be null");
        }
        enums = type.getEnumConstants();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getCode(), JdbcType.SMALLINT.TYPE_CODE);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (!rs.wasNull()){
            return byCode(rs.getInt(columnName));
        }
        return null;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (!rs.wasNull()){
            return byCode(rs.getInt(columnIndex));
        }
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (!cs.wasNull()){
            return byCode(cs.getInt(columnIndex));
        }
        return null;
    }

    private T byCode(int code){
        for (T value : enums) {
            if (code == value.getCode()){
                return value;
            }
        }
        return null;
    }
}
