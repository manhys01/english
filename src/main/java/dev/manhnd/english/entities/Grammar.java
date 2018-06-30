package dev.manhnd.english.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grammar")
public class Grammar {

	@Id
	private Long id;

	@Column
	private String lesson;

	@Column
	private String path;

	public Grammar() {
		super();
	}

	public Grammar(Long id, String lesson, String path) {
		super();
		this.id = id;
		this.lesson = lesson;
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLesson() {
		return lesson;
	}

	public void setLesson(String lesson) {
		this.lesson = lesson;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return String.format("Grammar [id=%s, lesson=%s, path=%s]", id, lesson, path);
	}

}
