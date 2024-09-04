package com.booking.mapper;

import com.booking.bean.comment.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment getRow(ResultSet resultSet) {
        try {
            Integer commentId = resultSet.getInt("comment_id");
            Integer roomtypeId = resultSet.getInt("roomtype_id");
            Integer employeeId = resultSet.getInt("employee_id");
            Integer memberId = resultSet.getInt("member_id");
            String commentScore = resultSet.getString("comment_score");
            String commentContent = resultSet.getString("comment_content");
            LocalDateTime createdTime = resultSet.getTimestamp("created_time").toLocalDateTime();
            String employeeReply = resultSet.getString("employee_reply");

            return new Comment(commentId, roomtypeId, employeeId, memberId, commentScore, commentContent, createdTime, employeeReply);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
