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

//    public static <T> T transactionalExecute(SqlTransaction<T> transaction) {
//        try (Connection conn = getConnection()) {
//            conn.setAutoCommit(false);
//            try {
//                T result = transaction.execute(conn);
//                conn.commit();
//                return result;
//            } catch (SQLException e) {
//                conn.rollback();
//                throw new StorageException(e);
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    public static <T> void transactionalExecute(String uuid, SqlTransaction<T> transaction) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                T result = transaction.execute(conn);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                if ("23505".equals(e.getSQLState())) {
                    throw new ExistStorageException(uuid);
                }
                throw new StorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}