package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Word;

public interface WordDAO {

	public Long createWord(Word w) throws Exception;

	public boolean updateWord(Word w) throws Exception;

	public boolean deleteWord(Word w) throws Exception;

	public Word getWord(long id) throws Exception;

	public Word getWord(String word) throws Exception;

	public List<Word> getWords() throws Exception;
}
