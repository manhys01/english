package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Grammar;

public class GrammarDAOImpl extends BaseDAOImpl implements GrammarDAO {

	@Override
	public Long createGrammar(Grammar g) throws Exception {
		return create(g);
	}

	@Override
	public boolean updateGrammar(Grammar g) throws Exception {
		return update(g);
	}

	@Override
	public boolean deleteGrammar(Grammar g) throws Exception {
		return delete(g);
	}

	@Override
	public Grammar getGrammar(int id) throws Exception {
		return (Grammar) get(Grammar.class, id);
	}
	
	@Override
	public Grammar getLastGrammar() throws Exception {
		return (Grammar) getLastRecord("FROM Grammar g ORDER BY g.id desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grammar> getGrammars() throws Exception {
		return (List<Grammar>) getResultList("FROM Grammar");
	}

	

}
