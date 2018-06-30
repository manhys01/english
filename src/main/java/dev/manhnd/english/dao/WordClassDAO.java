package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.WordClass;

public interface WordClassDAO {
	
	public Long createWordClass(WordClass w) throws Exception, DuplicateUniqueColumnException;

	public boolean updateWordClass(WordClass w) throws Exception, DuplicateUniqueColumnException;

	public boolean deleteWordClass(WordClass w) throws Exception;

	public WordClass getWordClass(long id) throws Exception;

	public WordClass getWordClass(String name) throws Exception;

	public List<WordClass> getWordClasses() throws Exception;
	
}
