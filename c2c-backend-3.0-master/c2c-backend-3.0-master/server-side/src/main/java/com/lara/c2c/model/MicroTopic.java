package com.lara.c2c.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "lov_micro_topics")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, 
property = "@micro_topic_id")*/
public class MicroTopic{	
	@Id	
	@Column(name = "micro_topic_id")
	private String microTopicId;
	
	@Column(name = "sub_topic_id")
	private int subTopicId;
	
	@Column(name = "micro_topic_name")
	private String microTopicName;
	
	@Column(name= "micro_topic_duration")
    private String microTopicDuration;		
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "micro_topic_id")
    private VideoTutorial videoDetail;
	
	@JsonBackReference
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "sub_topic_id", insertable=false, updatable=false)
	private SubTopic subTopic;
	
}
