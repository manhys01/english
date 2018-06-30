package dev.manhnd.english.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.scene.image.ImageView;

@Entity
@Table(name = "vocabularies")
public class Vocabulary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "wordid")
	private Word word;

	@ManyToOne
	@JoinColumn(name = "word_classesid")
	private WordClass wordClass;

	@Column(name = "definition", length = 255)
	private String definition;

	@Column(name = "meaning", length = 255)
	private String meaning;

	@Column(name = "image", length = 255)
	private String image;

	@Transient
	private ImageView imageView;

	@Column(name = "created", insertable = false, updatable = false)
	private Date created;

	@Column(name = "created", insertable = false, updatable = false)
	private Date lastestUpdate;

	public Vocabulary() {
	}

	public Vocabulary(Word word, WordClass wordClass, String definition, String meaning, String image) {
		super();
		this.word = word;
		this.wordClass = wordClass;
		this.definition = definition;
		this.meaning = meaning;
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public WordClass getWordClass() {
		return wordClass;
	}

	public void setWordClass(WordClass wordClass) {
		this.wordClass = wordClass;
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

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
		if (imageView != null) {
			this.imageView.setFitHeight(40);
			this.imageView.setPreserveRatio(true);
			this.imageView.setSmooth(true);
			this.imageView.setCache(true);
		}
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastestUpdate() {
		return lastestUpdate;
	}

	public void setLastestUpdate(Date lastestUpdate) {
		this.lastestUpdate = lastestUpdate;
	}

	@Override
	public String toString() {
		return String.format("%s %s\n(%s) %s\n%s", word.getWord(), word.getIpa(), wordClass.getName(), definition,
				meaning);
	}
}
