package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Sentence;

public interface SentenceDAO {

	public Long createSentence(Sentence s) throws Exception;

	public boolean updateSentence(Sentence s) throws Exception;

	public boolean deleteSentence(Sentence s) throws Exception;

	public Sentence getSentence(long id) throws Exception;
	
	public Sentence getSentence(String sentence) throws Exception;
	
	public List<Sentence> getSentences() throws Exception;
	
	public List<Sentence> getSentencesWhereIPAIsNull() throws Exception;
	
}
