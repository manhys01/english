package dev.manhnd.english.dao;

public class DAOFactory {

	private static PhraseDAO phraseDAO;
	private static PhraseDetailDAO phraseDetailDAO;
	private static SentenceDAO sentenceDAO;
	private static WordDAO wordDAO;
	private static WordClassDAO wordClassDAO;
	private static VocabularyDAO vocabularyDAO;
	private static GrammarDAO grammarDAO;
	private static GrammarQuestionDAO grammarQuestionDAO;
	private static GrammarAnswerDAO grammarAnswerDAO;

	public static PhraseDAO getPhraseDAO() {
		if (phraseDAO == null)
			phraseDAO = new PhraseDAOImpl();
		return phraseDAO;
	}

	public static PhraseDetailDAO getPhraseDetailDAO() {
		if (phraseDetailDAO == null)
			phraseDetailDAO = new PhraseDetailDAOImpl();
		return phraseDetailDAO;
	}

	public static SentenceDAO getSentenceDAO() {
		if (sentenceDAO == null)
			sentenceDAO = new SentenceDAOImpl();
		return sentenceDAO;
	}

	public static WordDAO getWordDAO() {
		if (wordDAO == null)
			wordDAO = new WordDAOImpl();
		return wordDAO;
	}

	public static WordClassDAO getWordClassDAO() {
		if (wordClassDAO == null)
			wordClassDAO = new WordClassDAOImpl();
		return wordClassDAO;
	}

	public static VocabularyDAO getVocabularyDAO() {
		if (vocabularyDAO == null)
			vocabularyDAO = new VocabularyDAOImpl();
		return vocabularyDAO;
	}

	public static GrammarQuestionDAO getGrammarQuestionDAO() {
		if (grammarQuestionDAO == null)
			grammarQuestionDAO = new GrammarQuestionDAOImpl();
		return grammarQuestionDAO;
	}

	public static GrammarAnswerDAO getGrammarAnswerDAO() {
		if (grammarAnswerDAO == null)
			grammarAnswerDAO = new GrammarAnswerDAOImpl();
		return grammarAnswerDAO;
	}

	public static GrammarDAO getGrammarDAO() {
		if (grammarDAO == null)
			grammarDAO = new GrammarDAOImpl();
		return grammarDAO;
	}

}
