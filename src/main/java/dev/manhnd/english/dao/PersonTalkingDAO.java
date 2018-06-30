package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.PersonTalking;

public interface PersonTalkingDAO {

	public Long createPersonTalking(PersonTalking p) throws Exception;

	public boolean updatePersonTalking(PersonTalking p) throws Exception;

	public boolean deletePersonTalking(PersonTalking p) throws Exception;

	public PersonTalking getPersonTalking(long id) throws Exception;

	public List<PersonTalking> getListPersonTalking() throws Exception;
}
