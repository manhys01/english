package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.PhraseDetail;

public interface PhraseDetailDAO {
	
	public Long createPhraseDetail(PhraseDetail p) throws Exception;

	public boolean updatePhraseDetail(PhraseDetail p) throws Exception;

	public boolean deletePhraseDetail(PhraseDetail p) throws Exception;

	public PhraseDetail getPhraseDetail(long id) throws Exception;
	
	public List<PhraseDetail> getPhraseDetails() throws Exception;
	
	public List<PhraseDetail> getPhraseDetails(String search) throws Exception;
}
