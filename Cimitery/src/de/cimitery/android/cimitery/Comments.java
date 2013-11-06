package de.cimitery.android.cimitery;

public class Comments {
	
	private long commentID;
	private long graveID;
	private String commentTitle;
	private String commentText;
	
	
	
	public String getCommentTitle() {
		return commentTitle;
	}
	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public long getCommentID() {
		return commentID;
	}
	public long getGraveID() {
		return graveID;
	}

}
