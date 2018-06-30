package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.GrammarAnswer;

public class GrammarAnswerDAOImpl extends BaseDAOImpl implements GrammarAnswerDAO {

	@Override
	public Long createGrammarAnswer(GrammarAnswer a) throws Exception {
		return create(a);
	}

	@Override
	public boolean updateGrammarAnswer(GrammarAnswer a) throws Exception {
		return update(a);
	}

	@Override
	public boolean deleteGrammarAnswer(GrammarAnswer a) throws Exception {
		return delete(a);
	}

	@Override
	public GrammarAnswer getGrammarAnswer(long id) throws Exception {
		return (GrammarAnswer) get(GrammarAnswer.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrammarAnswer> getGrammarAnswers() throws Exception {
		return (List<GrammarAnswer>) getResultList("FROM GrammarAnswer");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrammarAnswer> getGrammarAnswers(String searchKey) throws Exception {
		return (List<GrammarAnswer>) find(
				String.format("FROM GrammarAnswer q WHERE q.question like '%s'", searchKey));
	}

}
