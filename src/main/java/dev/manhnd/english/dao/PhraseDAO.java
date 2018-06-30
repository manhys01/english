package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Phrase;

public interface PhraseDAO {
	
	public Long createPhrase(Phrase p) throws Exception;

	public boolean updatePhrase(Phrase p) throws Exception;

	public boolean deletePhrase(Phrase p) throws Exception;

	public Phrase getPhrase(long id) throws Exception;
	
	public Phrase getPhrase(String phrase) throws Exception;
	
	public List<Phrase> getPhrases() throws Exception;
	
	public List<Phrase> getPhrasesWhereIPAisEmpty() throws Exception;
	
}
