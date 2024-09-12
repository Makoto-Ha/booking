package com.booking.bean.comment;

import java.time.LocalDateTime;

import com.booking.bean.admin.Admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id") // 對應資料庫中的主鍵欄位
	private Integer commentId;

	@Column(name = "roomtype_id") // 對應房型ID
	private Integer roomtypeId;
	
	@Column(name = "admin_id") // 員工ID
	private Integer adminId;

	@Column(name = "member_id") // 對應會員ID
	private Integer memberId;

	@Column(name = "comment_score") // 評分
	private String commentScore;

	@Column(name = "comment_content") // 評論內容
	private String commentContent;

	@Column(name = "created_time") // 創建時間
	private LocalDateTime createdTime;

	@Column(name = "admin_reply") // 員工回覆
	private String adminReply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id", insertable = false, updatable = false)
	private Admin admin;

	public Comment(Integer commentId, Integer roomtypeId, Integer adminId, Integer memberId, String commentScore,
			String commentContent, LocalDateTime createdTime, String adminReply) {
		this.commentId = commentId;
		this.roomtypeId = roomtypeId;
		this.adminId = adminId;
		this.memberId = memberId;
		this.commentScore = commentScore;
		this.commentContent = commentContent;
		this.createdTime = createdTime;
		this.adminReply = adminReply;
	}

	public Comment(Integer roomtypeId, Integer adminId, Integer memberId, String commentScore, String commentContent,
			String adminReply) {
		this.roomtypeId = roomtypeId;
		this.adminId = adminId;
		this.memberId = memberId;
		this.commentScore = commentScore;
		this.commentContent = commentContent;
		this.adminReply = adminReply;
	}

	public Comment(Integer commentId, Integer adminId, String adminReply) {
		this.commentId = commentId;
		this.adminId = adminId;
		this.adminReply = adminReply;
	}

	public Comment() {
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getRoomtypeId() {
		return roomtypeId;
	}

	public void setRoomtypeId(Integer roomtypeId) {
		this.roomtypeId = roomtypeId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getCommentScore() {
		return commentScore;
	}

	public void setCommentScore(String commentScore) {
		this.commentScore = commentScore;
	}

	public String getAdminReply() {
		return adminReply;
	}

	public void setAdminReply(String adminReply) {
		this.adminReply = adminReply;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", roomtypeId=" + roomtypeId + ", memberId=" + memberId
				+ ", commentScore=" + commentScore + ", adminReply=" + adminReply + ", createdTime=" + createdTime
				+ ", commentContent=" + commentContent + ", adminId=" + adminId + ", admin=" + admin + "]";
	}

}
