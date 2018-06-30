package dev.manhnd.english.utils;

import java.util.List;

import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Phrase;
import dev.manhnd.english.entities.Sentence;

public class IdInconsecutiveness {
	public static void main(String[] args) throws Exception {
//		List<Phrase> list = DAOFactory.getPhraseDAO().getPhrases();
//		findPhrase(list);
		
		List<Sentence> list = DAOFactory.getSentenceDAO().getSentences();
		findSentences(list);
		HibernateUtil.closeSessionFactory();
	}
	
	public static void findPhrase(List<Phrase> phrases) {
		Phrase current = phrases.get(0);
		for (int i = 1; i < phrases.size(); i++) {
			Phrase next = phrases.get(i);
			if(current.getId()!=next.getId()-1) {
				System.out.println("current: " + current.getId() + "| next: " + next.getId());
			}
			current = next;
		}
	}
	
	public static void findSentences(List<Sentence> sentences) {
		Sentence current = sentences.get(0);
		for (int i = 1; i < sentences.size(); i++) {
			Sentence next = sentences.get(i);
			if(current.getId()!=next.getId()-1) {
				System.out.println("current: " + current.getId() + "| next: " + next.getId());
			}
			current = next;
		}
	}
	
}
