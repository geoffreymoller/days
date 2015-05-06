package com.geoffreymoller.vision.days.service.repository.mapper;

import com.geoffreymoller.vision.days.service.domain.Day;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DayMapper implements ResultSetMapper<Day> {
    public static DayMapper INSTANCE = new DayMapper();

    public Day map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
        return new Day(
                rs.getLong("id"),
                rs.getLong("user_id"),
                new DateTime(rs.getDate("date")),
                rs.getString("journal"));
    }
}
