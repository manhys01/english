package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Sentence;

public class SentenceDAOImpl extends BaseDAOImpl implements SentenceDAO {

	@Override
	public Long createSentence(Sentence s) throws Exception, DuplicateUniqueColumnException {
		if (s.getSentence() == null || s.getSentence().isEmpty())
			throw new Exception("Sentence can not empty!");
		Sentence sentence = getSentence(s.getSentence());
		if (sentence != null)
			throw new DuplicateUniqueColumnException("Duplicate sentence!");
		return create(s);
	}

	@Override
	public boolean updateSentence(Sentence s) throws Exception, DuplicateUniqueColumnException {
		if (s.getSentence() == null || s.getSentence().isEmpty())
			throw new Exception("Sentence can not empty!");
		Sentence sentence = getSentence(s.getSentence());
		if (sentence != null)
			if (!s.getId().equals(sentence.getId()))
					throw new DuplicateUniqueColumnException("Duplicate sentence!");
		return update(s);
	}

	@Override
	public boolean deleteSentence(Sentence s) throws Exception {
		return delete(s);
	}

	@Override
	public Sentence getSentence(long id) throws Exception {
		return (Sentence) get(Sentence.class, id);
	}

	@Override
	public Sentence getSentence(String sentence) throws Exception {
		return (Sentence) get(Sentence.class, "sentence", sentence);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sentence> getSentences() throws Exception {
		return (List<Sentence>) getResultList("FROM Sentence");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sentence> getSentencesWhereIPAIsNull() throws Exception {
		return (List<Sentence>) getResultList("FROM Sentence s WHERE length(trim(s.ipa))=0");
	}
}
