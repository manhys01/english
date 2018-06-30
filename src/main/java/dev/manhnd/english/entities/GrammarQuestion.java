package dev.manhnd.english.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grammarquestions")
public class GrammarQuestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String question;
	private String meaning;
	private String suggestion;
	private int day;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="question")
	private List<GrammarAnswer> answers;

	public GrammarQuestion() {
	}

	public GrammarQuestion(String question, String meaning, String suggestion, int day) {
		super();
		this.question = question;
		this.meaning = meaning;
		this.suggestion = suggestion;
		this.day = day;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<GrammarAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<GrammarAnswer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return String.format("GrammarQuestion [id=%s, question=%s, meaning=%s, suggestion=%s, day=%s]", id, question,
				meaning, suggestion, day);
	}
}
