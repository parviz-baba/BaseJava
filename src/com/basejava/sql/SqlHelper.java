package com.basejava.sql;

import com.basejava.config.Config;
import com.basejava.exception.ExistStorageException;
import com.basejava.exception.StorageException;
import com.basejava.storage.SqlStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private static final ConnectionFactory connectionFactory =
            ((SqlStorage) Config.getInstance().getStorage()).connectionFactory;

    public static Connection getConnection() throws SQLException {
        return connectionFactory.getConnection();
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

    public static void transactionalExecute(String uuid, SqlTransaction<?> transaction) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                transaction.execute(conn);
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