package com.basejava.sql;

import com.basejava.config.Config;
import com.basejava.exception.ExistStorageException;
import com.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private static final String DB_URL = Config.getInstance().get("db.url");
    private static final String DB_USER = Config.getInstance().get("db.user");
    private static final String DB_PASSWORD = Config.getInstance().get("db.password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void execute(String sql, SqlExecutor executor, String uuid) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.execute(ps);
            ps.execute();
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException(uuid);
            }
            throw new StorageException(e);
        }
    }
}