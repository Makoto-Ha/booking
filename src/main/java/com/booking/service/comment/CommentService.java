package com.booking.service.comment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import com.booking.bean.comment.Comment;
import com.booking.dao.comment.CommentDaoImpl;
import com.booking.dto.comment.CommentDTO;
import com.booking.utils.Listable;
import com.booking.utils.Result;
import com.booking.utils.util.DaoResult;

import jakarta.servlet.ServletException;

public class CommentService {
	
	
	private  CommentDaoImpl commentDao;
	
    public CommentService(Session session) {
    	this.commentDao = new CommentDaoImpl(session);
    }

    /**
	 * 新建評論
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    public Result<Integer> addComment(Comment comment) {
        DaoResult<?> createCommentResult = commentDao.createComment(comment);
        if (createCommentResult.isFailure()) {
            return Result.failure("新增失敗");
        }
        return Result.success(createCommentResult.getGeneratedId());
    }
    /**
	 * 獲取所有評論
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    public Result<List<Listable>> getAllComments() {
    	DaoResult<List<Comment>> daoResult = commentDao.getAllComments();
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
        DaoResult<?> daoResult = commentDao.updateComment(comment);
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
    public Result<String> replyToComment(Comment comment) {
        DaoResult<?> updateCommentResult = commentDao.updateComment(comment);
        if (updateCommentResult.isFailure()) {
            return Result.failure("回覆評論失敗");
        }
        return Result.success("回覆評論成功");
    }
    /**
   	 * 依ID刪除評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<String>removeCommentById(Integer commentId) {
        DaoResult<?> deleteCommentByIdResult = commentDao.deleteCommentById(commentId);
        if (deleteCommentByIdResult.isFailure()) {
            return Result.failure("删除評論失敗");
        }
        return Result.success("刪除評論成功");
    }
    /**
   	 * 依員工ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 * 
   	 * 	public Result<Attraction> getAttractionById(Integer attractionId) {
		DaoResult<Attraction> daoResult = attractionDao.getAttractionById(attractionId);
		Attraction attraction = daoResult.getData();
		if(daoResult.isFailure()) {
			return Result.failure("沒有此景點");
		}
		return Result.success(attraction);
	}
   	 */
    public Result<List<Comment>> getCommentsByEmployeeId(Integer employeeId) {
     
        DaoResult<List<Comment>> commentsByEmployeeIdResult = commentDao.getCommentsByEmployeeId(employeeId);
        
        
        
        if(commentsByEmployeeIdResult.isFailure()) {
        	 return Result.failure("查詢員工評論失敗");
        }
        
        List<Comment> comments = commentsByEmployeeIdResult.getData();

        return Result.success(comments);
    }
 
    /**
   	 * 依會員ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Comment> getCommentsByMemberId(Integer memberId) {
    	// 還沒有會員表，先擱置
    	return null;
    }
    /**
   	 * 依房型ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Comment> getCommentsByRoomtypeId(Integer roomtypeId) {
    	// 房型還沒有外鍵，先擱置
    	return null;
    }
    /**
   	 * 依評論ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    public Result<Comment> getCommentById(Integer commentId) {
        DaoResult<Comment> getCommentsByRoomtypeId = commentDao.getCommentById(commentId);
        Comment comment = getCommentsByRoomtypeId.getData();
        if (getCommentsByRoomtypeId.isFailure()) {
            return Result.failure("查詢評論失敗");
        }
        return Result.success(comment);
    }
    /**
   	 * 模糊查詢
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    
    public Result<List<Listable>> searchComments(Comment comment) {
		DaoResult<List<Comment>> daynamicQueryResult = commentDao.dynamicQuery(comment);
		
		if(daynamicQueryResult.isFailure()) {
			return Result.failure("模糊查詢房間類型失敗");
		}

		List<Comment> comments = daynamicQueryResult.getData();
		
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
