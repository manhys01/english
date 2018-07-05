package dev.manhnd.english.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.Sentence;

public class IPASentenceApp {
	public static void main(String[] args) throws Exception {
		List<Sentence> sentences = DAOFactory.getSentenceDAO().getSentencesWhereIPAIsNull();
		double lastTime = System.currentTimeMillis();
//		sentences.forEach(e->{
//			System.out.println(e.getSentence());
//		});
		new IPASentenceApp().update(sentences);
		double currentTime = System.currentTimeMillis();
		System.out.println("Tổng thời gian = " + (currentTime - lastTime) / 1000);
		HibernateUtil.closeSessionFactory();
	}

	public void update(List<Sentence> sentences) throws URISyntaxException, IOException {
		File file = new File(getClass().getResource("/path/ipasentence.txt").toURI());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while (line != null) {
			String[] temp = line.split("\t");
			sentences.forEach(s -> {
				try {
					if (s.getSentence().equals(temp[0])) {
						String ipa = temp[1].trim();
						ipa = StringUtils.formatIPA(ipa);
						s.setIpa(ipa);
						DAOFactory.getSentenceDAO().updateSentence(s);
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
