package api.domain;

public class Author {
	long id;
	String nickname;
	
	public Author() {
	}
	
	public Author(long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
