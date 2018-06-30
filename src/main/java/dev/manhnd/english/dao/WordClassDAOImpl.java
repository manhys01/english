package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.WordClass;

public class WordClassDAOImpl extends BaseDAOImpl implements WordClassDAO {

	@Override
	public Long createWordClass(WordClass w) throws Exception, DuplicateUniqueColumnException {
		if (w.getName() == null || w.getName().isEmpty())
			throw new Exception("WordClass can not empty!");
		WordClass wordClass = getWordClass(w.getName());
		if (wordClass != null)
			throw new DuplicateUniqueColumnException("Duplicate WordClass!");
		return create(w);
	}

	@Override
	public boolean updateWordClass(WordClass w) throws Exception, DuplicateUniqueColumnException {
		if (w.getName() == null || w.getName().isEmpty())
			throw new Exception("WordClass can not empty!");
		WordClass wordClass = getWordClass(w.getName());
		if (wordClass != null)
			if (!w.getId().equals(wordClass.getId()))
				throw new DuplicateUniqueColumnException("Duplicate WordClass!");
		return update(w);
	}

	@Override
	public boolean deleteWordClass(WordClass w) throws Exception {
		return delete(w);
	}

	@Override
	public WordClass getWordClass(long id) throws Exception {
		return (WordClass) get(WordClass.class, id);
	}

	@Override
	public WordClass getWordClass(String name) throws Exception {
		return (WordClass) get(WordClass.class, "name", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WordClass> getWordClasses() throws Exception {
		return (List<WordClass>) getResultList("FROM WordClass");
	}

}
