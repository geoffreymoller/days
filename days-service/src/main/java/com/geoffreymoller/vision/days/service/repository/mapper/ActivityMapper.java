package com.geoffreymoller.vision.days.service.repository.mapper;

import com.geoffreymoller.vision.days.service.domain.Activity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityMapper implements ResultSetMapper<Activity> {
    public static ActivityMapper INSTANCE = new ActivityMapper();

    public Activity map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
        return new Activity(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("day_id"),
                rs.getDate("date"),
                rs.getString("name"),
                rs.getString("tags"),
                rs.getString("duration"),
                rs.getLong("duration_ms"),
                rs.getLong("count"),
                rs.getString("notes"));
    }
}
