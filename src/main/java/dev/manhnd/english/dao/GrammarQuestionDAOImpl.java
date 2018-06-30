package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.GrammarQuestion;

public class GrammarQuestionDAOImpl extends BaseDAOImpl implements GrammarQuestionDAO {

	@Override
	public Long createGrammarQuestion(GrammarQuestion q) throws Exception {
		return create(q);
	}

	@Override
	public boolean updateGrammarQuestion(GrammarQuestion q) throws Exception {
		return update(q);
	}

	@Override
	public boolean deleteGrammarQuestion(GrammarQuestion q) throws Exception {
		return delete(q);
	}

	@Override
	public GrammarQuestion getGrammarQuestion(long id) throws Exception {
		return (GrammarQuestion) get(GrammarQuestion.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrammarQuestion> getGrammarQuestions() throws Exception {
		return (List<GrammarQuestion>) getResultList("FROM GrammarQuestion");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrammarQuestion> getGrammarQuestion(String searchKey) throws Exception {
		return (List<GrammarQuestion>) find(
				String.format("FROM GrammarQuestion q WHERE q.question like '%s'", searchKey));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrammarQuestion> getGrammarQuestionsByDay(long day) throws Exception {
		return (List<GrammarQuestion>) find("FROM GrammarQuestion q WHERE q.day = " + day);
	}

	@Override
	public GrammarQuestion getGrammarQuestion(String targetKey, long day) throws Exception {
		return (GrammarQuestion) get(
				String.format("FROM GrammarQuestion q WHERE q.question='%s' and q.day=%d", targetKey, day));
	}

}
