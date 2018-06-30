package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.PhraseDetail;

public class PhraseDetailDAOImpl extends BaseDAOImpl implements PhraseDetailDAO {

	@Override
	public Long createPhraseDetail(PhraseDetail p) throws Exception {
		return create(p);
	}

	@Override
	public boolean updatePhraseDetail(PhraseDetail p) throws Exception {
		return update(p);
	}

	@Override
	public boolean deletePhraseDetail(PhraseDetail p) throws Exception {
		return false;
	}

	@Override
	public PhraseDetail getPhraseDetail(long id) throws Exception {
		return (PhraseDetail) get(PhraseDetail.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhraseDetail> getPhraseDetails() throws Exception {
		return (List<PhraseDetail>) getResultList("FROM PhraseDetail");
	}

	@Override
	public List<PhraseDetail> getPhraseDetails(String search) throws Exception {
		return null;
	}

}
