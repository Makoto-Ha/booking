package com.booking.service.comment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.booking.bean.comment.Comment;
import com.booking.dao.comment.CommentDao;
import com.booking.dto.comment.CommentDTO;
import com.booking.utils.DaoResult;
import com.booking.utils.Listable;
import com.booking.utils.Result;

import jakarta.servlet.ServletException;

public class CommentService {

    private final CommentDao commentDao = new CommentDao(); // 實體化數據訪問層
    /**
	 * 新建評論
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    public Result<Integer> addComment(Comment comment) {
        DaoResult<Integer> daoResult = commentDao.addComment(comment);
        if (daoResult.getAffectedRows() == null) {
            return Result.failure("新增失敗");
        }
        return Result.success(daoResult.getGeneratedId());
    }
    /**
	 * 獲取所有評論
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    public Result<List<Listable>> getAllComments() {
    	DaoResult<Comment> daoResult = commentDao.getAllComments();
    	List<Comment> comments = daoResult.getData();
		List<Listable> lists = new ArrayList<>();
		for(Comment comment : comments) {
			CommentDTO commentDTO = new CommentDTO();
			try {
				BeanUtils.copyProperties(commentDTO, comment);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			} 
			lists.add(commentDTO);
		}
		if(comments == null) {
			return Result.failure("查詢所有房間類型失敗");
		}
		return Result.success(lists);
	}
    /**
	 * 更新評論
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    public Result<Boolean> updateComment(Comment comment) {
        if (comment == null || comment.getCommentId() == null || comment.getCommentId() <= 0) {
            return Result.failure("無效的評論數據");
        }
        DaoResult<Integer> daoResult = commentDao.updateComment(comment);
        if (daoResult.getAffectedRows() == null || daoResult.getAffectedRows() <= 0) {
            return Result.failure("更新評論失敗");
        }
        return Result.success(true);
    }
    /**
	 * 員工回覆評論
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    public Result<Boolean> replyToComment(int commentId, int employeeId, String employeeReply) {
        if (commentId <= 0 || employeeId <= 0 || employeeReply == null || employeeReply.isEmpty()) {
            return Result.failure("無效的數據用於回覆評論");
        }
        DaoResult<Integer> daoResult = commentDao.replyToComment(commentId, employeeId, employeeReply);
        if (daoResult.getAffectedRows() == null || daoResult.getAffectedRows() <= 0) {
            return Result.failure("回覆評論失敗");
        }
        return Result.success(true);
    }
    /**
   	 * 依ID刪除評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Boolean> deleteComment(Integer commentId) {
        if (commentId == null || commentId <= 0) {
            return Result.failure("無效的評論ID");
        }
        DaoResult<Integer> daoResult = commentDao.deleteComment(commentId);
        if (daoResult.getAffectedRows() == null || daoResult.getAffectedRows() <= 0) {
            return Result.failure("删除評論失敗");
        }
        return Result.success(true);
    }
    /**
   	 * 依員工ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Comment> getCommentsByEmployeeId(Integer employeeId) {
     
        DaoResult<Comment> daoResult = commentDao.getCommentsByEmployeeId(employeeId);
        if (daoResult.getData() == null ) {
            return Result.failure("查詢員工評論失敗");
        }
        return Result.success(daoResult.getEntity());
    }
    /**
   	 * 依會員ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Comment> getCommentsByMemberId(Integer memberId) {
        DaoResult<Comment> daoResult = commentDao.getCommentsByMemberId(memberId);
        if (daoResult.getData() == null ) {
            return Result.failure("查詢評論失敗");
        }
        return Result.success(daoResult.getEntity());
    }
    /**
   	 * 依房型ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Comment> getCommentsByRoomtypeId(Integer roomtypeId) {
        DaoResult<Comment> daoResult = commentDao.getCommentsByRoomtypeId(roomtypeId);
        if (daoResult.getData() == null ) {
            return Result.failure("查詢房型評論失敗");
        }
        return Result.success(daoResult.getEntity());
    }
    /**
   	 * 依評論ID查詢評論，包含業務邏輯，如ID驗證
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Comment> getCommentById(Integer commentId) {
        DaoResult<Comment> daoResult = commentDao.getCommentById(commentId);
        if (daoResult.getData() == null) {
            return Result.failure("查詢評論失敗");
        }
        return Result.success(daoResult.getEntity());
    }
    /**
   	 * 模糊查詢
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    
    public Result<List<Listable>> searchComments(Comment comment) {
		DaoResult<Comment> daynamicQueryDaoResult = commentDao.dynamicQuery(comment);
		List<Comment> comments = daynamicQueryDaoResult.getData();
		
		if(comments == null) {
			return Result.failure("模糊查詢房間類型失敗");
		}
		List<Listable> commentsDTO = new ArrayList<>();
		for(Comment commentOne : comments) {
			CommentDTO commentDTO = new CommentDTO();
			try {
				BeanUtils.copyProperties(commentDTO, commentOne);
				commentsDTO.add(commentDTO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return Result.success(commentsDTO);
	}
	
}
