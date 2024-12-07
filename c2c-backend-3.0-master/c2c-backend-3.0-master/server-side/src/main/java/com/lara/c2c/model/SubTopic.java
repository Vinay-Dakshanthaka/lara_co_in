package com.lara.c2c.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "lov_sub_topics")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, 
property = "@id")*/
public class SubTopic{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sub_topic_id")
	private int subTopicId;	
	
	@Column(name = "topic_id")
	private int topicId;	
	
	@Column(name = "sub_topic_name")
	private String subTopicName;
	
	@Column(name = "sub_topic_duration")
	private String subTopicDuration;
	
	@JsonBackReference
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "topic_id", insertable=false, updatable=false)
    private Topic topic;
	
	/*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "sub_topic_id")
	private Set<MicroTopic> microTopic;*/
	
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "sub_topic_id")
	private List<MicroTopic> microTopic;
	
	//@JsonManagedReferenc
	
}
