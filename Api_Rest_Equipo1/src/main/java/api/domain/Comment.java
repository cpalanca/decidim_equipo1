package api.domain;

public class Comment {	
	int id;
    int id_authors;
    String body;
    String id_comments;
    String created_at;
    int down_votes;
    int up_votes;
    String reply_to;
    int reply_level;
    
    public Comment() {}
    
	public Comment(int id, int id_authors, String body, String id_comments, String created_at, int down_votes,
			int up_votes, String reply_to, int reply_level) {
		this.id = id;
		this.id_authors = id_authors;
		this.body = body;
		this.id_comments = id_comments;
		this.created_at = created_at;
		this.down_votes = down_votes;
		this.up_votes = up_votes;
		this.reply_to = reply_to;
		this.reply_level = reply_level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_authors() {
		return id_authors;
	}

	public void setId_authors(int id_authors) {
		this.id_authors = id_authors;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getId_comments() {
		return id_comments;
	}

	public void setId_comments(String id_comments) {
		this.id_comments = id_comments;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getDown_votes() {
		return down_votes;
	}

	public void setDown_votes(int down_votes) {
		this.down_votes = down_votes;
	}

	public int getUp_votes() {
		return up_votes;
	}

	public void setUp_votes(int up_votes) {
		this.up_votes = up_votes;
	}

	public String getReply_to() {
		return reply_to;
	}

	public void setReply_to(String reply_to) {
		this.reply_to = reply_to;
	}

	public int getReply_level() {
		return reply_level;
	}

	public void setReply_level(int reply_level) {
		this.reply_level = reply_level;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", id_authors=" + id_authors + ", body=" + body + ", id_comments=" + id_comments
				+ ", created_at=" + created_at + ", down_votes=" + down_votes + ", up_votes=" + up_votes + ", reply_to="
				+ reply_to + ", reply_level=" + reply_level + "]";
	}
	
	
	
	
    
    

}
