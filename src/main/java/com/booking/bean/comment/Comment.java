package com.booking.bean.comment;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer roomtypeId; // 房間類型編號
	private Integer memberId; // 會員編號
	private String commentScore; // 評分值
	private String employeeReply; // 管理員回覆
	private LocalDateTime createdTime; // 評論時間
	private String commentContent; // 評論內容
	private Integer commentId; // 旅館評論編號
	private Integer employeeId; // 員工編號

	// Getter 和 Setter 方法
	public Integer getCommentId() {
		return commentId;
	}

	public Integer getRoomtypeId() {
		return roomtypeId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public String getCommentScore() {
		return commentScore;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public String getEmployeeReply() {
		return employeeReply;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public void setRoomtypeId(Integer roomtypeId) {
		this.roomtypeId = roomtypeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setCommentScore(String commentScore) {
		this.commentScore = commentScore;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public void setEmployeeReply(String employeeReply) {
		this.employeeReply = employeeReply;
	}

	@Override
	public String toString() {
		return "Comment{" + "commentId=" + commentId + ", roomtypeId=" + roomtypeId + ", employeeId=" + employeeId
				+ ", memberId=" + memberId + ", commentScore='" + commentScore + '\'' + ", commentContent='"
				+ commentContent + '\'' + ", createdTime=" + createdTime + ", employeeReply='" + employeeReply + '\''
				+ '}';
	}

	public Comment(Integer commentId, Integer roomtypeId, Integer employeeId, Integer memberId, String commentScore,
			String commentContent, LocalDateTime createdTime, String employeeReply) {
		this.commentId = commentId;
		this.roomtypeId = roomtypeId;
		this.employeeId = employeeId;
		this.memberId = memberId;
		this.commentScore = commentScore;
		this.commentContent = commentContent;
		this.createdTime = createdTime;
		this.employeeReply = employeeReply;
	}

	  public Comment(Integer roomtypeId, Integer employeeId, Integer memberId, 
              String commentScore, String commentContent, String employeeReply) {
   this.roomtypeId = roomtypeId;
   this.employeeId = employeeId;
   this.memberId = memberId;
   this.commentScore = commentScore;
   this.commentContent = commentContent;
   this.employeeReply = employeeReply;
}

	
	public Comment() {
		// TODO Auto-generated constructor stub
	}

}
