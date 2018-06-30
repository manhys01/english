package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.GrammarAnswer;

public interface GrammarAnswerDAO {

	public Long createGrammarAnswer(GrammarAnswer a) throws Exception;

	public boolean updateGrammarAnswer(GrammarAnswer a) throws Exception;

	public boolean deleteGrammarAnswer(GrammarAnswer a) throws Exception;

	public GrammarAnswer getGrammarAnswer(long id) throws Exception;

	public List<GrammarAnswer> getGrammarAnswers() throws Exception;
	
	public List<GrammarAnswer> getGrammarAnswers(String searchKey) throws Exception;
}
