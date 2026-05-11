package com.chatapp.infrastructure.persistence.sqlite;

import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.application.ports.UserRepositoryPort;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.UserId;
import com.chatapp.infrastructure.persistence.entity.MessageEntity;
import com.chatapp.infrastructure.persistence.mapper.MessageMapper;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SqliteMessageRepository implements MessageRepositoryPort {

    private static final String URL = "jdbc:sqlite:chat.db";

    private final UserRepositoryPort userRepository;

    public SqliteMessageRepository(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
        initDatabase();
    }

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS messages (
                    id TEXT PRIMARY KEY,
                    user_id TEXT NOT NULL,
                    content TEXT NOT NULL,
                    timestamp TEXT NOT NULL,
                    FOREIGN KEY(user_id) REFERENCES users(id)
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Error initializing messages table", e);
        }
    }

    @Override
    public void save(ChatMessage message) {

        // 🔴 VALIDACIÓN: el usuario debe existir
        userRepository.findById(message.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + message.getUserId().value()));

        String sql = "INSERT INTO messages(id, user_id, content, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, message.getId().value());
            stmt.setString(2, message.getUserId().value());
            stmt.setString(3, message.getContent().value());
            stmt.setString(4, message.getTimestamp().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving message", e);
        }
    }

    @Override
    public List<ChatMessage> findAll() {

        List<ChatMessage> messages = new ArrayList<>();

        String sql = "SELECT * FROM messages";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                MessageEntity entity = new MessageEntity();
                entity.id = rs.getString("id");
                entity.userId = rs.getString("user_id");
                entity.content = rs.getString("content");
                entity.timestamp = Instant.parse(rs.getString("timestamp"));

                messages.add(MessageMapper.toDomain(entity));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching messages", e);
        }

        return messages;
    }

    @Override
    public List<ChatMessage> findByUser(UserId userId) {

        List<ChatMessage> messages = new ArrayList<>();

        String sql = "SELECT * FROM messages WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId.value());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                MessageEntity entity = new MessageEntity();
                entity.id = rs.getString("id");
                entity.userId = rs.getString("user_id");
                entity.content = rs.getString("content");
                entity.timestamp = Instant.parse(rs.getString("timestamp"));

                messages.add(MessageMapper.toDomain(entity));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching messages by user", e);
        }

        return messages;
    }
}