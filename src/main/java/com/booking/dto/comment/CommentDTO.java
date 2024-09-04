package com.booking.dto.comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.utils.Listable;

public class CommentDTO implements Listable {
    private Integer commentId;
    private Integer roomtypeId;
    private Integer memberId;
    private String commentScore;
    private String commentContent;
    private String createdTime;
    private String employeeReply;

    private static String[] attrs = { "member-id", "comment-score", "comment-content", "created-time", "employee-reply" };
    private static String[] attrsChinese = { "會員ID", "評分", "管理員回覆內容", "創建時間", "會員評論" };
    public static List<Map<String, String>> listInfos = new ArrayList<>();
    
    private static String[] pages = { "評論列表"};
    private static String[] pageURL = {"/booking/comment"};
    public static List<Map<String, String>> pageInfos = new ArrayList<>();
    
    public static String manageListName = "評論列表";

    static {
        for (int i = 0; i < attrs.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("attr", attrs[i]);
            map.put("attrChinese", attrsChinese[i]);
            listInfos.add(map);
        }
        
        for(int i=0; i<pages.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("page", pages[i]);
            map.put("url", pageURL[i]);
            pageInfos.add(map);
        }
    }

    public CommentDTO() {}

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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getEmployeeReply() {
        return employeeReply;
    }

    public void setEmployeeReply(String employeeReply) {
        this.employeeReply = employeeReply;
    }

    public String[] getAttrs() {
        return attrs;
    }

    public String[] getAttrsChinese() {
        return attrsChinese;
    }

    @Override
    public String getName() {
        return commentContent;
    }

    @Override
    public Integer getId() {
        return commentId;
    }

    @Override
    public String toString() {
        return "CommentDTO [commentId=" + commentId + ", roomtypeId=" + roomtypeId + ", memberId=" + memberId 
                + ", commentScore=" + commentScore + ", commentContent=" + commentContent 
                + ", createdTime=" + createdTime + ", employeeReply=" + employeeReply 
                + ", attrs=" + Arrays.toString(attrs) + ", attrsChinese=" + Arrays.toString(attrsChinese) + "]";
    }

    @Override
    public Map<String, Object> getAdditionProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        // 設置最大顯示長度
        int maxLength = 15;
        String comma = "";

        // 處理評論內容，使其長度不超過 maxLength
        String truncatedCommentContent = commentContent.length() > maxLength
            ? commentContent.substring(0, maxLength) + comma
            : commentContent;

        // 將評分值轉換為星星圖示
        String stars = "";
        try {
            int score = Integer.parseInt(commentScore);
            stars = "★".repeat(Math.max(0, score)) + "☆".repeat(Math.max(0, 5 - score));
        } catch (NumberFormatException e) {
            // 評分值無法轉換為整數時顯示空白
            stars = "☆☆☆☆☆";
        }

        // 設置附加屬性
        properties.put("評論內容", truncatedCommentContent);       
        properties.put("評分值", stars);
        properties.put("創建時間", createdTime);
       

        return properties;
    }

	@Override
	public Integer getTotalCounts() {
		return null;
	}

}
