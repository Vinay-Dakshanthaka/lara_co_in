package com.lara.c2c.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PackageData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String PackageName;
  private String StartDate;
  private String DemoStartDate;
  private String DemoEndDate;
  private String Duration;
  private String ScholarshipDate;
  private Boolean selected;
}
