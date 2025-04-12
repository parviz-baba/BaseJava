package com.basejava.storage;

import com.basejava.exception.NotExistStorageException;
import com.basejava.model.*;
import com.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

// TODO implement Section (except OrganizationSection)
// TODO Join and split ListSection by `\n`
public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT * FROM resume r " +
                "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        Resume resume = new Resume(uuid, rs.getString("full_name"));
                        do {
                            addContact(rs, resume);
                        } while (rs.next());
                        return resume;
                    }
                }
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContact(conn, r);
            deleteSections(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();

            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid, resume);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = map.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM sections")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = map.get(rs.getString("resume_uuid"));
                    if (resume != null) {
                        addSection(rs, resume);
                    }
                }
            }

            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            try (ResultSet rs = st.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO sections (resume_uuid, section_type, section_content) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());

                Section section = entry.getValue();
                if (section instanceof TextSection) {
                    ps.setString(3, ((TextSection) section).getContent());
                } else if (section instanceof ListSection) {
                    String joined = String.join("\n", ((ListSection) section).getItems());
                    ps.setString(3, joined);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM sections WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String type = rs.getString("section_type");
        String content = rs.getString("section_content");
        SectionType sectionType = SectionType.valueOf(type);

        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                r.addSection(sectionType, new TextSection(content));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> items = Arrays.asList(content.split("\n"));
                r.addSection(sectionType, new ListSection(items));
                break;
        }
    }
}