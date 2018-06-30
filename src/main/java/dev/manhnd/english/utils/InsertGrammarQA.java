package dev.manhnd.english.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import dev.manhnd.english.dao.DAOFactory;
import dev.manhnd.english.entities.GrammarAnswer;
import dev.manhnd.english.entities.GrammarQuestion;

public class InsertGrammarQA {

	public static void main(String[] args) {
		List<GrammarQuestion> questions = new ArrayList<>();
		List<GrammarAnswer> answers = new ArrayList<>();
		File file = new File("D:/test.txt");
		int day = 148;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			int count = 1;
			GrammarQuestion q = null;
			while (line != null) {
				line = line.trim();
				if (line != null) {
					GrammarAnswer a1 = new GrammarAnswer();
					GrammarAnswer a2 = new GrammarAnswer();
					GrammarAnswer a3 = new GrammarAnswer();
					GrammarAnswer a4 = new GrammarAnswer();
					switch (count) {
					case 1:
						q = new GrammarQuestion();
						q.setQuestion(line.substring(3));
						q.setDay(day);
						Long id = DAOFactory.getGrammarQuestionDAO().createGrammarQuestion(q);
						if (id > 0) {
							q.setId(id);
							questions.add(q);
						}
						break;
					case 13:
					case 25:
					case 37:
					case 49:
					case 61:
						q = new GrammarQuestion();
						q.setQuestion(line.substring(2));
						q.setDay(day);
						Long id1 = DAOFactory.getGrammarQuestionDAO().createGrammarQuestion(q);
						if (id1 > 0) {
							q.setId(id1);
							questions.add(q);
						}
						break;
					case 4:
					case 16:
					case 28:
					case 40:
					case 52:
					case 64:
						if (!line.equals("Không có nội dung cho mục này")) {
							q.setMeaning(line);
							DAOFactory.getGrammarQuestionDAO().updateGrammarQuestion(q);
						}
						break;
					case 7:
					case 19:
					case 31:
					case 43:
					case 55:
					case 67:
						if (!line.equals("Không có nội dung cho mục này")) {
							q.setMeaning(line);
							DAOFactory.getGrammarQuestionDAO().updateGrammarQuestion(q);
						}
						break;
					case 8:
					case 20:
					case 32:
					case 44:
					case 56:
					case 68:
						addAnswer(q, answers, line, a1, "\\(a\\)");
						break;
					case 9:
					case 21:
					case 33:
					case 45:
					case 57:
					case 69:
						addAnswer(q, answers, line, a2, "\\(b\\)");
						break;
					case 10:
					case 22:
					case 34:
					case 46:
					case 58:
					case 70:
						addAnswer(q, answers, line, a3, "\\(c\\)");
						break;
					case 11:
					case 23:
					case 35:
					case 47:
					case 59:
					case 71:
						addAnswer(q, answers, line, a4, "\\(d\\)");
						break;
					default:
						break;
					}
				}
				line = reader.readLine();
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		for (int i = 0; i < questions.size(); i++) {
			System.out.println(questions.get(i).toString());
			int count = 0;
			for (int j = i * 4; j < i * 4 + 4; j++) {
				System.out.println(answers.get(j).toString());
				count++;
				if (count == 4) {
					break;
				}
			}
			System.out.println("----------------");
		}
		HibernateUtil.closeSessionFactory();

	}

	private static void addAnswer(GrammarQuestion q, List<GrammarAnswer> answers, String line, GrammarAnswer answer,
			String regex) {
		if (!line.equals("Không có nội dung cho mục này")) {
			String[] split = line.split("\t");
			int ac = 0;
			for (int i = 0; i < split.length; i++) {
				ac++;
				if (!split[i].trim().isEmpty()) {
					String e = split[i].replaceAll(regex, "").trim();
					switch (ac) {
					case 1:
						answer.setAnswer(e);
						answer.setQuestion(q);
						break;
					case 2:
						if (e.equals("Không có nội dung cho mục này"))
							answer.setMeaning(null);
						else
							answer.setMeaning(e);
						break;
					case 3:
						answer.setDescription(e);
						if (e.toLowerCase().startsWith("đúng")) {
							answer.setRightAnswer(true);
						} else {
							answer.setRightAnswer(false);
						}
						try {
							Long id = DAOFactory.getGrammarAnswerDAO().createGrammarAnswer(answer);
							if(id>0) {
								answers.add(answer);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}
}
