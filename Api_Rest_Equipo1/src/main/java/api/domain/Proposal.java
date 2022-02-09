package api.domain;

import java.util.Date;

public class Proposal {
	int id;
    String title;
    int id_authors;
    String id_comments;
    int n_endorsement;
    Date created_at;
    Date published_at;
    
    public Proposal() {}

	public Proposal(int id, String title, int id_authors, String id_comments, int n_endorsement, Date created_at,
			Date published_at) {
		this.id = id;
		this.title = title;
		this.id_authors = id_authors;
		this.id_comments = id_comments;
		this.n_endorsement = n_endorsement;
		this.created_at = created_at;
		this.published_at = published_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId_authors() {
		return id_authors;
	}

	public void setId_authors(int id_authors) {
		this.id_authors = id_authors;
	}

	public String getId_comments() {
		return id_comments;
	}

	public void setId_comments(String id_comments) {
		this.id_comments = id_comments;
	}

	public int getN_endorsement() {
		return n_endorsement;
	}

	public void setN_endorsement(int n_endorsement) {
		this.n_endorsement = n_endorsement;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getPublished_at() {
		return published_at;
	}

	public void setPublished_at(Date published_at) {
		this.published_at = published_at;
	}

	@Override
	public String toString() {
		return "Proposal [id=" + id + ", title=" + title + ", id_authors=" + id_authors + ", id_comments=" + id_comments
				+ ", n_endorsement=" + n_endorsement + ", created_at=" + created_at + ", published_at=" + published_at
				+ "]";
	}
    
    

}
