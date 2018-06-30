package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.GrammarQuestion;

public interface GrammarQuestionDAO {

	public Long createGrammarQuestion(GrammarQuestion q) throws Exception;

	public boolean updateGrammarQuestion(GrammarQuestion q) throws Exception;

	public boolean deleteGrammarQuestion(GrammarQuestion q) throws Exception;

	public GrammarQuestion getGrammarQuestion(long id) throws Exception;
	
	public GrammarQuestion getGrammarQuestion(String targetKey, long day) throws Exception;

	public List<GrammarQuestion> getGrammarQuestions() throws Exception;
	
	public List<GrammarQuestion> getGrammarQuestionsByDay(long day) throws Exception;
	
	public List<GrammarQuestion> getGrammarQuestion(String searchKey) throws Exception;
}
