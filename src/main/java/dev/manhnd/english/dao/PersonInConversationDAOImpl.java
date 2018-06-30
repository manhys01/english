package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.PersonInConversation;

public class PersonInConversationDAOImpl extends BaseDAOImpl implements PersonInConversationDAO {

	@Override
	public Long createPersonInConversation(PersonInConversation p) throws Exception {
		return create(p);
	}

	@Override
	public boolean updatePersonInConversation(PersonInConversation p) throws Exception {
		return update(p);
	}

	@Override
	public boolean deletePersonInConversation(PersonInConversation p) throws Exception {
		return delete(p);
	}

	@Override
	public PersonInConversation getPersonInConversation(long id) throws Exception {
		return (PersonInConversation) get(PersonInConversation.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonInConversation> getListPersonInConversation() throws Exception {
		return (List<PersonInConversation>) getResultList("FROM PersonInConversation");
	}

}
