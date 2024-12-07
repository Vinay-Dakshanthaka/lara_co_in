package com.lara.c2c.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cb_banners")
public class Banner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banner_id")
	private int bannerId;
	
	@Column(name = "banner_title")
	private String banner_title;
	
	@Column(name = "banner_text")
	private String bannerText;
	
	@Column(name = "banner_image")
	private String bannerImage;
}
