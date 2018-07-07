package dev.manhnd.english.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grammaranswers")
public class GrammarAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String answer;
	private String meaning;
	private String description;
	
	@Column(name="rightanswer")
	private boolean rightAnswer;

	@ManyToOne
	@JoinColumn(name = "grammarquestionid")
	private GrammarQuestion question;

	public GrammarAnswer() {
		super();
	}

	public GrammarAnswer(String answer, String meaning, String description, boolean rightAnswer) {
		super();
		this.answer = answer;
		this.meaning = meaning;
		this.description = description;
		this.rightAnswer = rightAnswer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public boolean isRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(boolean rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public GrammarQuestion getQuestion() {
		return question;
	}

	public void setQuestion(GrammarQuestion question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return String.format("GrammarAnswer [id=%s, answer=%s, meaning=%s, description=%s, rightAnswer=%s]", id, answer,
				meaning, description, rightAnswer);
	}

}
