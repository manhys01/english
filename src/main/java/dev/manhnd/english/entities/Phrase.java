package dev.manhnd.english.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phrases")
public class Phrase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "phrase", length = 255, nullable = false, unique = true)
	private String phrase;

	@Column(name = "ipa", length = 255, unique = true)
	private String ipa;

	@Column(name = "created", insertable = false, updatable = false)
	private Date created;

	@Column(name = "latestupdate", insertable = false, updatable = false)
	private Date latestUpdate;
	
	public Phrase() {
	}

	public Phrase(String phrase, String ipa) {
		super();
		this.phrase = phrase;
		this.ipa = ipa;
	}

	public Phrase(Long id, String phrase, String ipa) {
		super();
		this.id = id;
		this.phrase = phrase;
		this.ipa = ipa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public String getIpa() {
		return ipa;
	}

	public void setIpa(String ipa) {
		this.ipa = ipa;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLatestUpdate() {
		return latestUpdate;
	}

	public void setLatestUpdate(Date latestUpdate) {
		this.latestUpdate = latestUpdate;
	}

	@Override
	public String toString() {
		return phrase;
	}

	public String formatPhrase() {
		return "Phrase: " + phrase + " IPA: " + ipa;
	}

}
