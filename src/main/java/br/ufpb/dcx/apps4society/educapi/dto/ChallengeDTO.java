package br.ufpb.dcx.apps4society.educapi.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.ufpb.dcx.apps4society.educapi.domain.Challenge;

public class ChallengeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotEmpty(message="Required")
	@Length(min=5, max=50, message="The size must be between 5 and 50 characters")
	private String word;
	
	private String soundUrl;
	private String videoUrl;
	private String imageUrl;
	
	public ChallengeDTO() {}
	
	public ChallengeDTO(Challenge obj) {
		this.id = obj.getId();
		this.word = obj.getWord();
		this.soundUrl = obj.getSoundUrl();
		this.videoUrl = obj.getVideoUrl();
		this.imageUrl = obj.getVideoUrl();
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

	public String getSoundUrl() {
		return soundUrl;
	}

	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
