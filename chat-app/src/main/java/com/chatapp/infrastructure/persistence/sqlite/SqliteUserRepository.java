package com.chatapp.infrastructure.persistence.sqlite;

import com.chatapp.application.ports.UserRepositoryPort;
import com.chatapp.domain.model.User;
import com.chatapp.domain.valueobject.UserId;

import java.sql.*;
import java.util.Optional;

public class SqliteUserRepository implements UserRepositoryPort {

    private static final String URL = "jdbc:sqlite:chat.db";

    public SqliteUserRepository() {
        init();
    }

    private void init() {

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id TEXT PRIMARY KEY,
                    password TEXT NOT NULL
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error creating users table",
                    e
            );
        }
    }

    @Override
    public void save(User user) {

        String sql =
                "INSERT INTO users(id, password) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getId().value());
            ps.setString(2, user.getPasswordHash());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error saving user",
                    e
            );
        }
    }

    @Override
    public Optional<User> findById(UserId id) {

        String sql =
                "SELECT id, password FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.value());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return Optional.of(
                        User.rehydrate(
                                new UserId(rs.getString("id")),
                                rs.getString("password")
                        )
                );
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error finding user",
                    e
            );
        }
    }
}