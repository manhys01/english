package dev.manhnd.english.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Phrase;

public class IPAPhraseApp {
	public static void main(String[] args) throws Exception {
		List<Phrase> phrases = DAOFactory.getPhraseDAO().getPhrasesWhereIPAisEmpty();
//		phrases.forEach(e->{
//			System.out.println(e.getPhrase());
//		});
		new IPAPhraseApp().update(phrases);
		HibernateUtil.closeSessionFactory();
	}

	public void update(List<Phrase> phrases) throws URISyntaxException, IOException {
		File file = new File(getClass().getResource("/path/ipaphrase.txt").toURI());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while (line != null) {
			String[] temp = line.split("\t");
			phrases.forEach(p -> {
				try {
					if (p.getPhrase().equals(temp[0])) {
						String ipa = temp[1].trim();
						ipa = StringUtils.formatIPA(ipa);
						p.setIpa(ipa);
						DAOFactory.getPhraseDAO().updatePhrase(p);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			line = br.readLine();
		}
		fr.close();
		br.close();
	}
}
