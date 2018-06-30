package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.PersonInConversation;

public interface PersonInConversationDAO {
	
	public Long createPersonInConversation(PersonInConversation p) throws Exception;

	public boolean updatePersonInConversation(PersonInConversation p) throws Exception;

	public boolean deletePersonInConversation(PersonInConversation p) throws Exception;

	public PersonInConversation getPersonInConversation(long id) throws Exception;

	public List<PersonInConversation> getListPersonInConversation() throws Exception;
	
}
