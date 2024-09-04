package com.booking.dao.comment;

import com.booking.bean.comment.Comment;  
import com.booking.mapper.CommentRowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

	/**
	 * 添加評論
	 * @param comment
	 * @return
	 */
    public DaoResult<Integer> addComment(Comment comment) {
        String sql = "INSERT INTO comments (roomtype_id, member_id, comment_score, comment_content, created_time) VALUES (?, ?, ?, ?, ?)";
        return DaoUtils.commonsUpdate(
                sql, 
                comment.getRoomtypeId(), 
                comment.getMemberId(), 
                comment.getCommentScore(), 
                comment.getCommentContent(), 
                Timestamp.valueOf(comment.getCreatedTime())
        );
    }


    /**
     * 獲得所有評論
     * @param comment
     * @return
     */
    public DaoResult<Comment> getAllComments() {
        String sql = "SELECT * FROM comments";
        return DaoUtils.commonsQuery(sql, new CommentRowMapper());
    }

    /**
     * 更新評論
     * @param comment
     * @return
     */
    public DaoResult<Integer> updateComment(Comment comment) {
        String sql = "UPDATE comments SET roomtype_id = ?, member_id = ?, comment_score = ?, comment_content = ? WHERE comment_id = ?";
        return DaoUtils.commonsUpdate(
                sql, 
                comment.getRoomtypeId(), 
                comment.getMemberId(), 
                comment.getCommentScore(), 
                comment.getCommentContent(), 
                comment.getCommentId()
        );
    }

    /**
     * 員工回復評論
     * @param comment
     * @return
     */
    public DaoResult<Integer> replyToComment(int commentId, int employeeId, String employeeReply) {
        String sql = "UPDATE comments SET employee_id = ?, employee_reply = ? WHERE comment_id = ?";
        return DaoUtils.commonsUpdate(sql, employeeId, employeeReply, commentId);
    }

    /**
     * 評論ID刪除評論
     * @param comment
     * @return
     */
    public DaoResult<Integer> deleteComment(Integer commentId) {
        String sql = "DELETE FROM comments WHERE comment_id = ?";
        return DaoUtils.commonsUpdate(sql, commentId);
    }
    /**
     * 評論ID查詢評論
     * @param comment
     * @return
     */
    public DaoResult<Comment> getCommentById(Integer commentId) {
    	String sql = "SELECT * FROM comments WHERE comment_id = ?";
    	return DaoUtils.commonsQuery(sql, new CommentRowMapper(), commentId);
    }

    /**
     * 員工ID查詢評論
     * @param comment
     * @return
     */
    public DaoResult<Comment> getCommentsByEmployeeId(Integer employeeId) {
        String sql = "SELECT * FROM comments WHERE employee_id = ?";
        return DaoUtils.commonsQuery(sql, new CommentRowMapper(), employeeId);
    }
    /**
     * 會員ID查詢評論
     * @param comment
     * @return
     */
    public DaoResult<Comment> getCommentsByMemberId(Integer memberId) {
        String sql = "SELECT * FROM comments WHERE member_id = ?";
        return DaoUtils.commonsQuery(sql, new CommentRowMapper(), memberId);
    }
    /**
     * 房型ID查詢評論
     * @param comment
     * @return
     */
    public DaoResult<Comment> getCommentsByRoomtypeId(Integer roomtypeId) {
        String sql = "SELECT * FROM comments WHERE roomtype_id = ?";
        return DaoUtils.commonsQuery(sql, new CommentRowMapper(), roomtypeId);
    }
    /**
     * 根據指定條件進行模糊查詢評論
     * @param roomtypeId 房型編號，用於模糊查詢
     * @param employeeId 員工編號，用於模糊查詢
     * @param memberId 會員編號，用於模糊查詢
     * @param commentScore 評分值，用於模糊查詢
     * @param commentContent 評論內容，用於模糊查詢
     * @param employeeReply 員工回覆，用於模糊查詢
     * @return 包含符合條件的評論列表的 DaoResult
     */
    public DaoResult<Comment> dynamicQuery(Comment comment) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Comments WHERE 1=1");
        List<Object> list = new ArrayList<>();
        Integer roomtypeId = comment.getRoomtypeId();
        Integer employeeId = comment.getEmployeeId();
        Integer memberId = comment.getMemberId();
        String commentScore = comment.getCommentScore();
        String commentContent = comment.getCommentContent();
        String employeeReply = comment.getEmployeeReply();

        if (roomtypeId != null) {
            sql.append(" AND roomtype_id = ?");
            list.add(roomtypeId);
        }
        if (employeeId != null) {
            sql.append(" AND employee_id = ?");
            list.add(employeeId);
        }
        if (memberId != null) {
            sql.append(" AND member_id = ?");
            list.add(memberId);
        }
        if (commentScore != null && !commentScore.trim().isEmpty()) {
            sql.append(" AND comment_score LIKE ?");
            list.add("%" + commentScore.trim() + "%");
        }
        if (commentContent != null && !commentContent.trim().isEmpty()) {
            sql.append(" AND comment_content LIKE ?");
            list.add("%" + commentContent.trim() + "%");
        }
        if (employeeReply != null && !employeeReply.trim().isEmpty()) {
            sql.append(" AND employee_reply LIKE ?");
            list.add("%" + employeeReply.trim() + "%");
        }

        return DaoUtils.commonsQuery(sql.toString(), new CommentRowMapper(), list.toArray());
    }


}


