package br.ufpb.dcx.apps4society.educapi.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.ufpb.dcx.apps4society.educapi.domain.User;
import org.hibernate.validator.constraints.Length;

import br.ufpb.dcx.apps4society.educapi.domain.Context;

public class ContextNewDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotEmpty(message="Required")
	@Length(min=2, max=50, message="The size must be between 5 and 50 characters")
	private String name;
	
	private String imageUrl;
	private String soundUrl;
	private String videoUrl;
	
	private User creator;
	
	public ContextNewDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
}
