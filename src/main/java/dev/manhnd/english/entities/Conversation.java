package dev.manhnd.english.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "conversations")
public class Conversation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String audio;
	
	@OneToMany(mappedBy="conversation", fetch=FetchType.EAGER)
	private Set<PersonInConversation> listPersonInThis; 

	public Conversation() {
	}

	public Conversation(String title, String audio) {
		super();
		this.title = title;
		this.audio = audio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}
	
	public Set<PersonInConversation> getListPersonInThis() {
		return listPersonInThis;
	}

	public void setListPersonInThis(Set<PersonInConversation> listPersonInThis) {
		this.listPersonInThis = listPersonInThis;
	}

	@Override
	public String toString() {
		return String.format("Conversation [id=%s, title=%s, audio=%s]", id, title, audio);
	}

}
