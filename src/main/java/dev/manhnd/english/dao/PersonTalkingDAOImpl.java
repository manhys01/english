package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.PersonTalking;

public class PersonTalkingDAOImpl extends BaseDAOImpl implements PersonTalkingDAO {

	@Override
	public Long createPersonTalking(PersonTalking p) throws Exception {
		return create(p);
	}

	@Override
	public boolean updatePersonTalking(PersonTalking p) throws Exception {
		return update(p);
	}

	@Override
	public boolean deletePersonTalking(PersonTalking p) throws Exception {
		return delete(p);
	}

	@Override
	public PersonTalking getPersonTalking(long id) throws Exception {
		return (PersonTalking) get(PersonTalking.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonTalking> getListPersonTalking() throws Exception {
		return (List<PersonTalking>) getResultList("FROM PersonTalking");
	}

}
