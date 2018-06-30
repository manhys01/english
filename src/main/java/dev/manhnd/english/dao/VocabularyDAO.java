package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Vocabulary;

public interface VocabularyDAO {
	
	public long createVocabulary(Vocabulary v) throws Exception;

	public boolean updateVocabulary(Vocabulary v) throws Exception;

	public boolean deleteVocabulary(Vocabulary v) throws Exception;

	public Vocabulary getVocabulary(long id) throws Exception;

	public List<Vocabulary> getVocabularies(String value) throws Exception;

	public List<Vocabulary> getVocabularies() throws Exception;
}
