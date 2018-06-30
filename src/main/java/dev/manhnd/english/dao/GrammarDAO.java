package dev.manhnd.english.dao;

import java.util.List;

import dev.manhnd.english.entities.Grammar;

public interface GrammarDAO {

	public Long createGrammar(Grammar g) throws Exception;

	public boolean updateGrammar(Grammar g) throws Exception;

	public boolean deleteGrammar(Grammar g) throws Exception;

	public Grammar getGrammar(int id) throws Exception;
	
	public Grammar getLastGrammar() throws Exception;
	
	public List<Grammar> getGrammars() throws Exception;

}
