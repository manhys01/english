package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Vocabulary;

public class VocabularyDAOImpl extends BaseDAOImpl implements VocabularyDAO {

	@Override
	public long createVocabulary(Vocabulary v) throws Exception {
		if (v == null)
			throw new Exception("Vocabulary can not null");
		if (v.getWord() == null)
			throw new Exception("Word can not null");
		if (v.getWordClass() == null)
			throw new Exception("Word class can not null");
		return create(v);
	}

	@Override
	public boolean updateVocabulary(Vocabulary v) throws Exception {
		if (v.getWord() == null)
			throw new Exception("Word can not null");
		if (v.getWordClass() == null)
			throw new Exception("Word class can not null");
		return update(v);
	}

	@Override
	public boolean deleteVocabulary(Vocabulary v) throws Exception {
		return delete(v);
	}

	@Override
	public Vocabulary getVocabulary(long id) throws Exception {
		return (Vocabulary) get(Vocabulary.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vocabulary> getVocabularies(String value) throws Exception {
		String condition = String.format("v WHERE v.word.word like '%s%' AND v.defVi like '%s%'", value, value);
		return (List<Vocabulary>) find("FROM Vocabulary " + condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vocabulary> getVocabularies() throws Exception {
		return (List<Vocabulary>) getResultList("FROM Vocabulary");
	}

}
