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
@Table(name = "persontalking")
public class PersonTalking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PersonInConversation_ID")
	private PersonInConversation personInConversation;

	@Column(name = "ordering")
	private int order;

	@Column
	private String sentence;

	@Column
	private String meaning;

	public PersonTalking() {
	}

	public PersonTalking(PersonInConversation personInConversation, int order, String sentence, String meaning) {
		super();
		this.personInConversation = personInConversation;
		this.order = order;
		this.sentence = sentence;
		this.meaning = meaning;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonInConversation getPersonInConversation() {
		return personInConversation;
	}

	public void setPersonInConversation(PersonInConversation personInConversation) {
		this.personInConversation = personInConversation;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	@Override
	public String toString() {
		return String.format("PersonTalking [id=%s, personInConversation=%s, order=%s, sentence=%s, meaning=%s]", id,
				personInConversation, order, sentence, meaning);
	}

}
