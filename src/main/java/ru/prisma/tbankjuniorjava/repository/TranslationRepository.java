package ru.prisma.tbankjuniorjava.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.prisma.tbankjuniorjava.entity.Translation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Translation request) {
        UUID id = UUID.randomUUID();
        var sql = "INSERT INTO audit_translation (id, user_ip, original_text, translated_text, timestamp) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, request.getUserIp(), request.getOriginalText(), request.getTranslatedText(), request.getTimestamp());
    }


    public List<Translation> getAll() {
        var sql = "SELECT id, user_ip, original_text, translated_text, timestamp FROM audit_translation";
        return jdbcTemplate.query(sql, new TranslationRowMapper());
    }

    private static class TranslationRowMapper implements RowMapper<Translation> {
        @Override
        public Translation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Translation.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .userIp(rs.getString("user_ip"))
                    .originalText(rs.getString("original_text"))
                    .translatedText(rs.getString("translated_text"))
                    .timestamp(rs.getTimestamp("timestamp").toLocalDateTime())
                    .build();
        }
    }
}
