package com.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor {
    void execute(PreparedStatement ps) throws SQLException;
}