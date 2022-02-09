package api.domain;

public class Author {
	int id;
	String nickname;
	
	public Author() {
	}
	
	public Author(int id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", nickname=" + nickname + "]";
	}
	
	
}
