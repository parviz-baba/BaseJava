package com.basejava.sql;

import org.postgresql.util.PSQLException;
import com.basejava.exception.ExistStorageException;
import com.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {

//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
