package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Phrase;

public class PhraseDAOImpl extends BaseDAOImpl implements PhraseDAO {

	@Override
	public Long createPhrase(Phrase p) throws Exception, DuplicateUniqueColumnException {
		if (p.getPhrase() == null || p.getPhrase().isEmpty())
			throw new RuntimeException("Phrase can not empty!");
		Phrase phrase = getPhrase(p.getPhrase());
		if (phrase != null)
			throw new DuplicateUniqueColumnException("Duplicate phrase!");
		return create(p);
	}

	@Override
	public boolean updatePhrase(Phrase p) throws Exception, DuplicateUniqueColumnException {
		if (p.getPhrase() == null || p.getPhrase().isEmpty())
			throw new RuntimeException("Phrase can not empty!");

		Phrase phrase = getPhrase(p.getPhrase());
		if (phrase != null)
			if (!phrase.getId().equals(p.getId()))
				throw new DuplicateUniqueColumnException("Duplicate phrase!");
		return update(p);
	}

	@Override
	public boolean deletePhrase(Phrase p) throws Exception {
		return delete(p);
	}

	@Override
	public Phrase getPhrase(long id) throws Exception {
		return (Phrase) get(Phrase.class, id);
	}

	@Override
	public Phrase getPhrase(String phrase) throws Exception {
		return (Phrase) get(Phrase.class, "phrase", phrase);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Phrase> getPhrases() throws Exception {
		return (List<Phrase>) getResultList("FROM Phrase");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Phrase> getPhrasesWhereIPAisEmpty() throws Exception {
		return (List<Phrase>) getResultList("FROM Phrase p WHERE length(trim(p.ipa))=0");
	}

}
