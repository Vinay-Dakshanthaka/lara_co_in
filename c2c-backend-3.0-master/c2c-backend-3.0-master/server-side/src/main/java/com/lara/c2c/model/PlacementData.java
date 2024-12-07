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
public class PlacementData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  private String date;
  
  private String CompanyName;
  
  private Integer StudentAttended;
  
  private Integer StudentPlaced;
  
  private Double SalaryPackage;
  
  private Boolean selected;
}
