package dev.manhnd.english.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "personinconversation")
public class PersonInConversation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@ManyToOne
	@JoinColumn(name = "Conversations_ID")
	private Conversation conversation;
	
	@OneToMany(mappedBy="personInConversation", fetch=FetchType.EAGER)
	private Set<PersonTalking> listPersonTalking;

	public PersonInConversation() {
		super();
	}

	public PersonInConversation(String name, Conversation conversation) {
		super();
		this.name = name;
		this.conversation = conversation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	
	public Set<PersonTalking> getListPersonTalking() {
		return listPersonTalking;
	}
	
	public void setListPersonTalking(Set<PersonTalking> listPersonTalking) {
		this.listPersonTalking = listPersonTalking;
	}

	@Override
	public String toString() {
		return String.format("PeopleInConversation [id=%s, name=%s, conversation=%s]", id, name, conversation);
	}

}
