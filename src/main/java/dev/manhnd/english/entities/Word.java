package dev.manhnd.english.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import dev.manhnd.english.utils.SimpleAudioPlayer;


@Entity
@Table(name = "words")
public class Word {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "word", nullable = false, unique = true, length = 255)
	private String word;

	@Column(name = "ipa", length = 255)
	private String ipa;

	@Column(name = "normal_audio")
	private String normalAudio;

	@Column(name = "special_audio")
	private String specialAudio;

	@Transient
	private SimpleAudioPlayer playAudio;

	@Transient
	private SimpleAudioPlayer playSpecialAudio;

	public Word() {
	}

	public Word(String word, String ipa, String normalAudio, String specialAudio) {
		super();
		this.word = word;
		this.ipa = ipa;
		this.normalAudio = normalAudio;
		this.specialAudio = specialAudio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getIpa() {
		return ipa;
	}

	public void setIpa(String ipa) {
		this.ipa = ipa;
	}

	public String getNormalAudio() {
		return normalAudio;
	}

	public void setNormalAudio(String normalAudio) {
		this.normalAudio = normalAudio;
	}

	public String getSpecialAudio() {
		return specialAudio;
	}

	public void setSpecialAudio(String specialAudio) {
		this.specialAudio = specialAudio;
	}

	public SimpleAudioPlayer getPlayAudio() {
		return playAudio;
	}

	public void setPlayAudio(SimpleAudioPlayer playAudio) {
		this.playAudio = playAudio;
	}

	public SimpleAudioPlayer getPlaySpecialAudio() {
		return playSpecialAudio;
	}

	public void setPlaySpecialAudio(SimpleAudioPlayer playSpecialAudio) {
		this.playSpecialAudio = playSpecialAudio;
	}

	@Override
	public String toString() {
		return word;
	}

}
