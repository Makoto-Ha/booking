package com.booking.controller.comment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import com.booking.bean.comment.Comment;
import com.booking.dto.comment.CommentDTO;
import com.booking.service.comment.CommentService; // 引入服务层
import com.booking.utils.JsonUtil;
import com.booking.utils.Listable;
import com.booking.utils.RequestParamUtils;
import com.booking.utils.Result;
import org.apache.commons.beanutils.BeanUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/comment/create",
		                   "/comment/create.jsp",
                           "/comment/delete", 
                           "/comment/update", 
                           "/comment/replyToComment",
                           "/comment/selectUpdate", 
                           "/comment/select",
                           "/comment/searchComments",
                           "/comment/selectByEmployeeId", 
                           "/comment/selectByRoomtypeId",
                           "/comment/selectByMemberId",
                           "/comment/select.jsp",
                           "/comment/list",
                           "/comment/edit.jsp",
                           "/getcommentjson",
                           "/comment"})
public class CommentController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CommentService commentService; 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String[] splitURI = requestURI.split("/");
        String path = splitURI[splitURI.length - 1];

        Session session = (Session) request.getAttribute("hibernateSession");
        commentService = new CommentService(session);
        
        switch (path) {
            case "select" -> select(request, response);
            case "create" -> create(request, response);
            case "create.jsp" -> sendCreateJsp(request, response);
            case "delete" -> delete(request, response);
            case "update" -> update(request, response);
            case "edit.jsp" -> sendEditJsp(request, response);
            case "comment" -> comment(request, response);
            case "replyToComment" -> replyToComment(request, response);
            case "select.jsp" -> sendSelectJsp(request, response);
            case "selectUpdate" -> selectUpdate(request, response);
            case "selectByEmployeeId" -> selectByEmployeeId(request, response); 
            case "selectByMemberId" -> selectByMemberId(request, response);      
            case "selectByRoomtypeId" -> selectByRoomtypeId(request, response); 
            case "searchComments" -> searchComments(request, response);  
            case "list" -> listComments(request, response);
            case "getcommentjson" -> getCommentJson(request, response);
        }
    }
    
    private void sendEditJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer commentId = Integer.parseInt(request.getParameter("comment-id"));
    	Result<Comment> commentServiceResult = commentService.getCommentById(commentId);
    	if(!commentServiceResult.isSuccess()) {
    		response.getWriter().write(commentServiceResult.getMessage());
    		return;
    	}
    	request.setAttribute("comment", commentServiceResult.getData());
    	
    	request.getRequestDispatcher("../adminsystem/comment/comment-edit.jsp").forward(request, response);
    }
    
    /**
	 * 返回room的json數據
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getCommentJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer commentId = Integer.parseInt(request.getParameter("comment-id"));
		Result<Comment> commentServiceResult = commentService.getCommentById(commentId);
		if(commentServiceResult.isFailure()) {
			response.getWriter().write(commentServiceResult.getMessage());
			return;
		}
		Comment comment = commentServiceResult.getData();
		CommentDTO commentDTO = new CommentDTO();
		try {
			BeanUtils.copyProperties(commentDTO, comment);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		String jsonData = JsonUtil.toJson(commentDTO);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonData);
	}

    /**
	 * 評論類型管理首頁
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    
    private void comment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
     
        Result<List<Listable>> commentServiceResult = commentService.getAllComments();
        if(!commentServiceResult.isSuccess()) {
            response.getWriter().write(commentServiceResult.getMessage());
            return;
        }
        List<Listable> comments = commentServiceResult.getData();
        request.setAttribute("lists", comments);
        request.setAttribute("pageInfos", CommentDTO.pageInfos); 
        request.setAttribute("listInfos", CommentDTO.listInfos); 
        request.setAttribute("manageListName", CommentDTO.manageListName);
        request.getRequestDispatcher("adminsystem/index.jsp").forward(request, response);
    }
    /**
	 * 顯示評論列表，獲取所有評論
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
    private void listComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result<List<Listable>> result = commentService.getAllComments();
        if (result.isSuccess()) {
            request.setAttribute("listComment", result.getData());
        } else {
            request.setAttribute("message", "無法獲取評論列表：" + result.getMessage());
        }
        request.getRequestDispatcher("/comment/SelectAllComments.jsp").forward(request, response);
    }
    /**
   	 *  新增評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));
            Integer memberId = Integer.parseInt(request.getParameter("member-id"));
            String commentScore = request.getParameter("comment-score");
            String commentContent = request.getParameter("comment-content");
            LocalDateTime createdTime = LocalDateTime.now();

            Comment newComment = new Comment();
            newComment.setRoomtypeId(roomtypeId);
            newComment.setMemberId(memberId);
            newComment.setCommentScore(commentScore);
            newComment.setCommentContent(commentContent);
            newComment.setCreatedTime(createdTime);

            Result<Integer> result = commentService.addComment(newComment);

            if (!result.isSuccess()) {
                response.getWriter().write("評論新增失敗：" + result.getMessage());
                return;
            }

            // 如果操作成功，重定向到評論列表页面
            response.sendRedirect("/booking/comment");
            
        } catch (Exception e) {
            response.getWriter().write("發生錯誤：" + e.getMessage());
        }
    }

    /**
	 * 轉去create.jsp
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendCreateJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("../adminsystem/comment/comment-create.jsp").forward(request, response);
	}
    /**
   	 *  顯示編輯評論的表單，根據評論ID獲取評論數據
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    private void selectUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer commentId = Integer.parseInt(request.getParameter("comment-id"));
        Result<Comment> result = commentService.getCommentById(commentId);
        
        if (result.isSuccess()) {
            request.setAttribute("comment", result.getData());
            request.getRequestDispatcher("/comment-form.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "無法獲取評論：" + result.getMessage());
            request.getRequestDispatcher("/comment/SelectAllComments.jsp").forward(request, response);
        }
    }
    /**
   	 *  員工回覆，根據用戶提交的表單更新數據
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    protected void replyToComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
            int commentId = Integer.parseInt(request.getParameter("comment-id"));
            int adminId = Integer.parseInt(request.getParameter("admin-id"));
            
            String adminReply = request.getParameter("admin-reply");
            
            Comment comment = new Comment(commentId, adminId, adminReply);
            
            Result<String> result = commentService.replyToComment(comment);

            if (result.isSuccess()) {
                // 成功後重新加載更新後的評論數據
                Result<Comment> updatedCommentResult = commentService.getCommentById(commentId);
                if (updatedCommentResult.isSuccess()) {
                    request.setAttribute("comment", updatedCommentResult.getData());
                }
                request.setAttribute("messageTitle", "回覆成功");
                request.setAttribute("messageContent", "評論已成功回覆。");
            } else {
                request.setAttribute("messageTitle", "回覆失敗");
                request.setAttribute("messageContent", "抱歉，評論回覆失敗：" + result.getMessage());
            }

      
        
        // 最後轉發到編輯頁面
        request.getRequestDispatcher("/comment").forward(request, response);
    }

    /**
   	 * 更新評論，根據用戶提交的表單更新數據
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int commentId = Integer.parseInt(request.getParameter("comment-Id"));
            int roomtypeId = Integer.parseInt(request.getParameter("roomtype-Id"));
            int memberId = Integer.parseInt(request.getParameter("member-Id"));
            String commentScore = request.getParameter("comment-Score");
            String commentContent = request.getParameter("comment-Content");

            Comment comment = new Comment();
            comment.setCommentId(commentId);
            comment.setRoomtypeId(roomtypeId);
            comment.setMemberId(memberId);
            comment.setCommentScore(commentScore);
            comment.setCommentContent(commentContent);

            Result<Boolean> result = commentService.updateComment(comment);

            if (result.isSuccess()) {
                response.sendRedirect(request.getContextPath() + "../adminsystem/comment/comment-edit.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "../adminsystem/comment/comment-edit.jsp");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("../adminsystem/comment/comment-edit.jsp");
        }
    }
    /**
   	 * 删除評論，依據ID刪除評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer commentId = Integer.parseInt(request.getParameter("comment-id"));
		Result<String> removeCommentResult = commentService.removeCommentById(commentId);
		
		response.getWriter().write(removeCommentResult.getMessage());	
	}
    /**
   	 * 依據評論ID查询評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    protected void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer commentId = Integer.parseInt(request.getParameter("comment-Id"));
            Result<Comment> result = commentService.getCommentById(commentId);
            if (result.isSuccess()) {
                request.setAttribute("comment", result.getData());
            } else {
                request.setAttribute("message", "找不到該評論：" + result.getMessage());
                return;
            }

            request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("message", "無效的評論ID格式");
            request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "發生錯誤：" + e.getMessage());
            request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
        }
    }
    /**
   	 * 依據員工ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    protected void selectByEmployeeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		Integer employeeId = Integer.parseInt(request.getParameter("employee-Id"));                    
    		Result<List<Comment>> result = commentService.getCommentsByEmployeeId(employeeId);
    		if (result.isSuccess()) {
    			request.setAttribute("comment", result.getData());
    		} else {
    			request.setAttribute("message", "找不到該員工的評論：" + result.getMessage());
    		}
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    		
    	} catch (NumberFormatException e) {
    		request.setAttribute("message", "無效的員工ID格式");
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    	} catch (Exception e) {
    		request.setAttribute("message", "發生錯誤：" + e.getMessage());
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    	}
    }
    /**
   	 * 依據會員ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */
    protected void selectByMemberId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	Integer memberId = Integer.parseInt(request.getParameter("member-Id")); 
        	Result<Comment> result = commentService.getCommentsByMemberId(memberId);
        	if (result.isSuccess()) {
    			request.setAttribute("comment", result.getData());
    		} else {
    			request.setAttribute("message", "找不到該員工的評論：" + result.getMessage());
    		}
         request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    		
    	} catch (NumberFormatException e) {
    		request.setAttribute("message", "無效的員工ID格式");
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    	} catch (Exception e) {
    		request.setAttribute("message", "發生錯誤：" + e.getMessage());
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    	}
    }
    /**
   	 * 依據房型ID查詢評論
   	 * @param request
   	 * @param response
   	 * @throws IOException
   	 * @throws ServletException
   	 */  
    protected void selectByRoomtypeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-Id"));      
            Result<Comment> result = commentService.getCommentsByRoomtypeId(roomtypeId);
            if (result.isSuccess()) {
    			request.setAttribute("comment", result.getData());
    		} else {
    			request.setAttribute("message", "找不到該員工的評論：" + result.getMessage());
    		}
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    		
    	} catch (NumberFormatException e) {
    		request.setAttribute("message", "無效的員工ID格式");
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    	} catch (Exception e) {
    		request.setAttribute("message", "發生錯誤：" + e.getMessage());
    		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
    	}
    }
    
	/**
	 * 轉到查詢
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void sendSelectJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		request.getRequestDispatcher("../adminsystem/comment/comment-select.jsp").forward(request, response);
	}
	
	/**
	 * 模糊查詢
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void searchComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    Integer roomtypeId = RequestParamUtils.getParameter(request, "roomtype-id", Integer.class);
	    Integer employeeId = RequestParamUtils.getParameter(request, "employee-id", Integer.class);
	    Integer memberId = RequestParamUtils.getParameter(request, "member-id", Integer.class);
	    String commentScore = RequestParamUtils.getParameter(request, "comment-score", String.class);
	    String commentContent = RequestParamUtils.getParameter(request, "comment-content", String.class);
	    String employeeReply = RequestParamUtils.getParameter(request, "employee-reply", String.class);
	    
	    Comment searchCriteria = new Comment(
	            roomtypeId, 
	            employeeId, 
	            memberId, 
	            commentScore,
	            commentContent,
	            employeeReply
	    );

	    Result<List<Listable>> commentServiceResult = commentService.searchComments(searchCriteria);


	    if (!commentServiceResult.isSuccess()) {
	        response.getWriter().write(commentServiceResult.getMessage());
	        return;
	    }
	    
	    request.setAttribute("lists", commentServiceResult.getData());
	    request.setAttribute("pageInfos", CommentDTO.pageInfos);
	    request.setAttribute("listInfos", CommentDTO.listInfos);
	    request.getRequestDispatcher("../adminsystem/index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    doGet(request, response);
	}
}

