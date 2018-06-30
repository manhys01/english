package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Word;

public class WordDAOImpl extends BaseDAOImpl implements WordDAO {

	@Override
	public Long createWord(Word w) throws Exception, DuplicateUniqueColumnException {
		if (w.getWord() == null || w.getWord().isEmpty())
			throw new Exception("Word can not empty!");
		Word word = getWord(w.getWord());
		if (word != null)
			throw new DuplicateUniqueColumnException("Duplicate Word!");
		return create(w);
	}

	@Override
	public boolean updateWord(Word w) throws Exception, DuplicateUniqueColumnException {
		if (w.getWord() == null || w.getWord().isEmpty())
			throw new Exception("Word can not empty!");
		Word word = getWord(w.getWord());
		if (word != null)
			if (!w.getId().equals(word.getId()))
				throw new DuplicateUniqueColumnException("Duplicate Word!");
		return update(w);
	}

	@Override
	public boolean deleteWord(Word w) throws Exception {
		return delete(w);
	}

	@Override
	public Word getWord(long id) throws Exception {
		return (Word) get(Word.class, id);
	}

	@Override
	public Word getWord(String word) throws Exception {
		return (Word) get(Word.class, "word", word);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> getWords() throws Exception {
		return (List<Word>) getResultList("FROM Word");
	}

}
