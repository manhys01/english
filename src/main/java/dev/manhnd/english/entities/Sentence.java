package dev.manhnd.english.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sentences")
public class Sentence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="sentence", length = 255, unique = true)
	private String sentence;

	@Column(name = "ipa", length = 255)
	private String ipa;

	@Column(name = "audio", length = 255)
	private String audio;

	@Column(name = "definition", length = 255)
	private String definition;

	@Column(name = "created", insertable = false, updatable = false)
	private Date created;

	@Column(name = "latestupdate", insertable = false, updatable = false)
	private Date latestUpdate;

	public Sentence() {
	}

	public Sentence(String sentence, String ipa, String audio, String definition) {
		super();
		this.sentence = sentence;
		this.ipa = ipa;
		this.audio = audio;
		this.definition = definition;
	}

	public Sentence(Long id, String sentence, String ipa, String audio, String definition) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.ipa = ipa;
		this.audio = audio;
		this.definition = definition;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getIpa() {
		return ipa;
	}

	public void setIpa(String ipa) {
		this.ipa = ipa;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
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
		return "Sentence [id=" + id + ", sentence=" + sentence + ", ipa=" + ipa + ", audio=" + audio + ", definition="
				+ definition + ", created=" + created + ", latestUpdate=" + latestUpdate + "]";
	}

	// @Override
	// public String toString() {
	// return sentence + "\n" + definition;
	// }

}
