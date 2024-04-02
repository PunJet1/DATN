package com.watch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class News implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int newsId;
	@Column(columnDefinition = "NVARCHAR(500) NULL")
	private String title;
	@Column(columnDefinition = "NVARCHAR(200) NULL")
	private String image;
	@Column(columnDefinition = "NVARCHAR(max) NULL")
	private String content;
	@Column(columnDefinition = "NVARCHAR(500) NULL")
	private String shortcontent;
	
	public String getShortcontent() {
		return shortcontent;
	}
	public void setShortcontent(String shortcontent) {
		this.shortcontent = shortcontent;
	}
	
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public News(int newsId, String title, String image, String content) {
		super();
		this.newsId = newsId;
		this.title = title;
		this.image = image;
		this.content = content;
	}
	
	public News() {
		// TODO Auto-generated constructor stub
	}
	
}
