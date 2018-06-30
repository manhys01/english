package dev.manhnd.english.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Grammar;
import dev.manhnd.english.entities.Phrase;
import dev.manhnd.english.entities.PhraseDetail;
import dev.manhnd.english.entities.Sentence;
import dev.manhnd.english.entities.Vocabulary;
import dev.manhnd.english.entities.Word;
import dev.manhnd.english.entities.WordClass;
import dev.manhnd.english.utils.HibernateUtil;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ApplicationDataModel {

	public static final String APPLICATION_ICON = "/images/icons/logo.png";
	public static final String SPLASH_IMAGE = "/images/loading.gif";

	private final static ApplicationDataModel instance = new ApplicationDataModel();

	public static ApplicationDataModel getInstance() {
		return instance;
	}

	private StringProperty loadingInformation = new SimpleStringProperty(this, "loadingInfomation", "");

	/*
	 * Lists
	 */
	private ObservableList<Phrase> phrases;
	private ObservableList<PhraseDetail> phraseDetails;
	private ObservableList<Sentence> sentences;
	private ObservableList<Word> words;
	private ObservableList<WordClass> wordClasses;
	private ObservableList<Vocabulary> vocabularies;
	private ObservableList<Grammar> grammers;

	/*
	 * Suggestion
	 */
	private SuggestionProvider<Phrase> phraseSuggestionProvider;
	private SuggestionProvider<PhraseDetail> phraseDetailSuggestionProvider;
	private SuggestionProvider<Sentence> sentenceSuggestionProvider;
	private SuggestionProvider<Word> wordsSuggestionProvider;
	private SuggestionProvider<WordClass> wordClassesSuggestionProvider;
	private SuggestionProvider<Vocabulary> vocabulariesSuggestionProvider;

	/*
	 * Application Audio Path
	 */
	private String sentencesAudioPath;
	private String normalWordsAudioPath;
	private String specialWordsAudioPath;
	private String vocabularyImagePath;
	private String grammarPath;

	private Properties prop;

	int count = 0;

	public ApplicationDataModel() {
		init();
	}

	private void init() {
		phrases = FXCollections.observableArrayList();
		phraseDetails = FXCollections.observableArrayList();
		sentences = FXCollections.observableArrayList();
		words = FXCollections.observableArrayList();
		wordClasses = FXCollections.observableArrayList();
		vocabularies = FXCollections.observableArrayList();
		grammers = FXCollections.observableArrayList();
	}

	public void loadDataModel() {
		try {
			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[1]);
			HibernateUtil.getSessionFactory().openSession().close();

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[2]);
			phrases.addAll(DAOFactory.getPhraseDAO().getPhrases());

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[3]);
			phraseDetails.addAll(DAOFactory.getPhraseDetailDAO().getPhraseDetails());

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[4]);
			sentences.addAll(DAOFactory.getSentenceDAO().getSentences());

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[5]);
			words.addAll(DAOFactory.getWordDAO().getWords());

//			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[6]);
//			words.forEach(w -> {
//				String normalAudio = w.getNormalAudio();
//				if (normalAudio != null) {
//					if (!normalAudio.trim().isEmpty()) {
//						try {
//							w.setPlayAudio(new SimpleAudioPlayer(new File(normalAudio)));
//						} catch (Exception e) {
//						}
//					}
//				}
//				String specialAudio = w.getSpecialAudio();
//				if (specialAudio != null) {
//					if (!specialAudio.trim().isEmpty()) {
//						try {
//							w.setPlaySpecialAudio(new SimpleAudioPlayer(new File(specialAudio)));
//						} catch (Exception e) {
//						}
//					}
//				}
//			});

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[7]);
			wordClasses.addAll(DAOFactory.getWordClassDAO().getWordClasses());

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[8]);
			vocabularies.addAll(DAOFactory.getVocabularyDAO().getVocabularies());

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[9]);
			vocabularies.forEach(v -> {
				try {
					ImageView imageView = new ImageView(new Image(new FileInputStream(new File(v.getImage()))));
					v.setImageView(imageView);
				} catch (FileNotFoundException e) {
				}
			});
			
			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[14]);
			grammers.addAll(DAOFactory.getGrammarDAO().getGrammars());

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[10]);
			createSuggestProvider();

			ApplicationDataModel.getInstance().setLoadingInformation(ApplicationConstant.STARTUP_TASKS[11]);
			loadAudioPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSuggestProvider() {
		phraseSuggestionProvider = SuggestionProvider.create(phrases);
		phraseDetailSuggestionProvider = SuggestionProvider.create(phraseDetails);
		sentenceSuggestionProvider = SuggestionProvider.create(sentences);
		wordsSuggestionProvider = SuggestionProvider.create(words);
		wordClassesSuggestionProvider = SuggestionProvider.create(wordClasses);
		vocabulariesSuggestionProvider = SuggestionProvider.create(vocabularies);
	}

	private void loadAudioPath() {
		try {
			prop = new Properties();
			prop.load(getClass().getResourceAsStream("/path/path.properties"));
			sentencesAudioPath = prop.getProperty("sentencesAudioPath");
			normalWordsAudioPath = prop.getProperty("normalWordsAudioPath");
			specialWordsAudioPath = prop.getProperty("specialWordsAudioPath");
			vocabularyImagePath = prop.getProperty("vocabularyImagePath");
			grammarPath = prop.getProperty("grammarPath");
		} catch (IOException e) {
			e.printStackTrace();
			sentencesAudioPath = "E:/Documents/EngProjectData/audio/";
			normalWordsAudioPath = "E:/Documents/EngProjectData/audio/";
			specialWordsAudioPath = "E:/Documents/EngProjectData/audio/";
			vocabularyImagePath = "E:/Documents/EngProjectData/images/";
			grammarPath = "E:/Documents/EngProjectData/grammar/";
		}
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public StringProperty loadingInformationProperty() {
		return loadingInformation;
	}

	public String getLoadingInformation() {
		return loadingInformation.get();
	}

	public void setLoadingInformation(String info) {
		loadingInformation.set(info);
	}

	/*
	 * 
	 * List
	 * 
	 */
	public final ObservableList<Phrase> getPhrases() {
		return phrases;
	}

	public final ObservableList<PhraseDetail> getPhraseDetails() {
		return phraseDetails;
	}

	public final ObservableList<Sentence> getSentences() {
		return sentences;
	}

	public final ObservableList<Word> getWords() {
		return words;
	}

	public final ObservableList<WordClass> getWordClasses() {
		return wordClasses;
	}

	public final ObservableList<Vocabulary> getVocabularies() {
		return vocabularies;
	}
	
	public final ObservableList<Grammar> getGrammars() {
		return grammers;
	}

	/*
	 * 
	 * Suggest providers
	 * 
	 */
	public final SuggestionProvider<Phrase> getPhraseSuggestionProvider() {
		return phraseSuggestionProvider;
	}

	public final SuggestionProvider<PhraseDetail> getPhraseDetailSuggestionProvider() {
		return phraseDetailSuggestionProvider;
	}

	public final SuggestionProvider<Sentence> getSentenceSuggestionProvider() {
		return sentenceSuggestionProvider;
	}

	public final SuggestionProvider<Word> getWordsSuggestionProvider() {
		return wordsSuggestionProvider;
	}

	public final SuggestionProvider<WordClass> getWordClassesSuggestionProvider() {
		return wordClassesSuggestionProvider;
	}

	public final SuggestionProvider<Vocabulary> getVocabulariesSuggestionProvider() {
		return vocabulariesSuggestionProvider;
	}

	/*
	 * 
	 * Application audio path getters and setters
	 * 
	 */
	public String getSentencesAudioPath() {
		return sentencesAudioPath;
	}

	public void setSentencesAudioPath(String sentencesAudioPath) {
		this.sentencesAudioPath = sentencesAudioPath;
	}

	public String getNormalWordsAudioPath() {
		return normalWordsAudioPath;
	}

	public void setNormalWordsAudioPath(String normalWordsAudioPath) {
		this.normalWordsAudioPath = normalWordsAudioPath;
	}

	public String getSpecialWordsAudioPath() {
		return specialWordsAudioPath;
	}

	public void setSpecialWordsAudioPath(String specialWordsAudioPath) {
		this.specialWordsAudioPath = specialWordsAudioPath;
	}

	public String getVocabularyImagePath() {
		return vocabularyImagePath;
	}

	public void setVocabularyImagePath(String vocabularyImagePath) {
		this.vocabularyImagePath = vocabularyImagePath;
	}
	
	public String getGrammarPath() {
		return grammarPath;
	}
	
	public void setGrammarPath(String grammarPath) {
		this.grammarPath = grammarPath;
	}

}
