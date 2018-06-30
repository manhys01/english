package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Conversation;
import dev.manhnd.english.entities.PersonTalking;

public interface ConversationDAO {

	public Long createConversation(Conversation c) throws Exception;

	public boolean updateConversation(Conversation c) throws Exception;

	public boolean deleteConversation(Conversation c) throws Exception;

	public Conversation getConversation(long id) throws Exception;

	public List<Conversation> getConversations() throws Exception;

	public List<PersonTalking> getConversationDetail(long id) throws Exception;

}
