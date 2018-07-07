package dev.manhnd.english.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phrasedetails")
public class PhraseDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "phraseid")
	private Phrase phrase;

	@Column
	private String definition;

	@Column
	private String meaning;

	@Column
	private String description;

	@Column(name = "created", insertable = false, updatable = false)
	private Date created;

	@Column(name = "latestupdate", insertable = false, updatable = false)
	private Date latestUpdate;

	public PhraseDetail() {
	}

	public PhraseDetail(Phrase phrase, String definition, String meaning, String description) {
		super();
		this.phrase = phrase;
		this.definition = definition;
		this.meaning = meaning;
		this.description = description;
	}

	public PhraseDetail(Long id, Phrase phrase, String definition, String meaning, String description) {
		super();
		this.id = id;
		this.phrase = phrase;
		this.definition = definition;
		this.meaning = meaning;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Phrase getPhrase() {
		return phrase;
	}

	public void setPhrase(Phrase phrase) {
		this.phrase = phrase;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return phrase.getPhrase() + "\n" + meaning;
	}

}
