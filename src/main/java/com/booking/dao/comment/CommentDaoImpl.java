package com.booking.dao.comment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.booking.bean.admin.Admin;
import com.booking.bean.comment.Comment;
import com.booking.utils.util.DaoResult;

public class CommentDaoImpl  implements  CommentDao {

    private Session session;
    
    public CommentDaoImpl(Session session) {
        this.session = session;
    }
    /**
     * 創建評論
     * @param comment
     * @return
     */
    public DaoResult<?> createComment(Comment comment) {
        session.persist(comment);  // Hibernate 的 persist 方法
        Integer commentId = comment.getCommentId();
        return DaoResult.create().setGeneratedId(commentId).setSuccess(commentId != null);
    }

    /**
     * 更新評論
     * @param comment
     * @return
     */
    public DaoResult<?> updateComment(Comment comment) {
    	String adminReply = comment.getAdminReply();
    	Integer adminId = comment.getAdminId();
    	Integer commentId = comment.getCommentId();
    	Comment oldComment = session.get(Comment.class, commentId);
    	oldComment.setAdminId(adminId);
    	oldComment.setAdminReply(adminReply);
        return DaoResult.create().setSuccess(oldComment != null);
    }

    /**
     * 刪除評論
     * @param commentId
     * @return
     */
    public DaoResult<?> deleteCommentById(Integer commentId) {
            Comment comment = session.get(Comment.class, commentId);
            if (comment != null) {
                session.remove(comment);  // Hibernate 的 delete 方法
                return DaoResult.create().setSuccess(true);
            }
            
        return DaoResult.create().setSuccess(false);
    }

    /**
     * 根據id查詢評論
     * @param commentId
     * @return
     */
    public DaoResult<Comment> getCommentById(Integer commentId) {
    	Comment comment = session.get(Comment.class, commentId);
        return DaoResult.create(comment).setSuccess(comment != null);  // Hibernate 的 get 方法    
    }

    /**
     * 查詢全部評論
     * @return
     */
    public DaoResult<List<Comment>> getAllComments() {
        Query<Comment> query = session.createQuery("FROM Comment", Comment.class);
        List<Comment> comments = query.getResultList();
        return DaoResult.create(comments).setSuccess(comments != null);
    }
    
    /**
     * 根據員工查找他的評論
     * @param employeeId
     * @return
     */
    public DaoResult<List<Comment>> getCommentsByEmployeeId(Integer employeeId) {
    	Admin admin = session.get(Admin.class, employeeId);
    	List<Comment> comments = admin.getComments();
    	return DaoResult.create(comments).setSuccess(comments != null);
    }   
    /**
     * 模糊查詢
     * @param employeeId
     * @return
     */
    public DaoResult<List<Comment>> dynamicQuery(Comment comment) {
    	String commentContent = comment.getCommentContent();
    	String commentScore = comment.getCommentScore();
    	String hql = "FROM Comment c WHERE 1=1";
    	if(commentContent != null) {
    		hql += "And c.commentContent LIKE :commentContent";
    	}
    	
    	if(commentScore != null) {
    		hql += "And c.commentScore LIKE :commentScore";
    	}
    	
    	Query<Comment> query = session.createQuery(hql, Comment.class);
    	
    	
    	if(commentContent != null) {
    		query.setParameter("commentContent","%"+ commentContent +"%");
    	}
    	
    	if(commentScore != null) {
    		query.setParameter("commentScore","%"+ commentScore + "%");
    	}
    	
    	List<Comment> comments = query.getResultList();
    	return DaoResult.create(comments).setSuccess(comments != null);
    }
}


