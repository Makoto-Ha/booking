package com.booking.dao.comment;

import java.util.List;
import com.booking.bean.comment.Comment;
import com.booking.utils.util.DaoResult;

public interface CommentDao {

	  /**
     * 創建評論
     * @param comment
     * @return
     */
    DaoResult<?> createComment(Comment comment);
    /**
     * 更新評論
     * @param comment
     * @return
     */
    DaoResult<?> updateComment(Comment comment);
    /**
     * 刪除評論
     * @param commentId
     * @return
     */
    DaoResult<?> deleteCommentById(Integer commentId);
    /**
     * 根據id查詢評論
     * @param commentId
     * @return
     */
    DaoResult<Comment> getCommentById(Integer commentId);
    /**
     * 查詢全部評論
     * @return
     */
    DaoResult<List<Comment>> getAllComments();
    /**
     * 根據員工查找他的評論
     * @param employeeId
     * @return
     */
    DaoResult<List<Comment>> getCommentsByEmployeeId(Integer employeeId);
    /**
     * 模糊查詢
     * @param employeeId
     * @return
     */
    DaoResult<List<Comment>> dynamicQuery(Comment comment);
}
