package com.basejava.storage;

import com.basejava.config.Config;
import com.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class SqlHelper {
    private static final String DB_URL = Config.getInstance().get("db.url");
    private static final String DB_USER = Config.getInstance().get("db.user");
    private static final String DB_PASSWORD = Config.getInstance().get("db.password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void execute(String sql, Consumer<PreparedStatement> consumer) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            consumer.accept(ps);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static <T> T executeQuery(String sql, Function<PreparedStatement, T> function) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return function.apply(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}