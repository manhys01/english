package dev.manhnd.english.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.manhnd.english.entities.Conversation;
import dev.manhnd.english.entities.PersonTalking;

public class ConversationDAOImpl extends BaseDAOImpl implements ConversationDAO {

	@Override
	public Long createConversation(Conversation c) throws Exception {
		return create(c);
	}

	@Override
	public boolean updateConversation(Conversation c) throws Exception {
		return update(c);
	}

	@Override
	public boolean deleteConversation(Conversation c) throws Exception {
		return delete(c);
	}

	@Override
	public Conversation getConversation(long id) throws Exception {
		return (Conversation) get(Conversation.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Conversation> getConversations() throws Exception {
		return (List<Conversation>) getResultList("FROM Conversation");
	}

	@Override
	public List<PersonTalking> getConversationDetail(long id) throws Exception {
		List<PersonTalking> list = new ArrayList<>();
		Conversation conversation = this.getConversation(id);
		conversation.getListPersonInThis().forEach(e -> {
			e.getListPersonTalking().forEach(p -> {
				list.add(p);
			});
		});
		Collections.sort(list, new Comparator<PersonTalking>() {
			@Override
			public int compare(PersonTalking o1, PersonTalking o2) {
				Integer i1 = o1.getOrder();
				Integer i2 = o2.getOrder();
				if (i1 > i2) {
					return 1;
				} else if (i1 < i2) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		return list;
	}

}
