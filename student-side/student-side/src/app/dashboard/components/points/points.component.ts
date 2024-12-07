import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PointsService } from '../../services/points.service';

@Component({
  selector: 'app-points',
  templateUrl: './points.component.html',
  styleUrls: ['./points.component.css']
})
export class PointsComponent implements OnInit {
  totalPoints:string ;
  videoPoints: string ;
  examPoints:string ;
  cumExamPoints:string ;
  lastUpdatedVideoPoints:string = 'NA';
  lastUpdatedExamPoints:string = 'NA';
  lastUpdatedCumExamPoints:string = 'NA';
  constructor(private router:Router, private activatedRoute:ActivatedRoute, private pointsService:PointsService) { }

  ngOnInit() {
    this.getLearnerPoints();
  }


  getLearnerPoints(){
    this.pointsService._getLearnerPoints().subscribe(data=>{
  
      this.videoPoints = this.assignFixedValue(data.videoPoints);
      this.totalPoints = this.assignFixedValue(data.totalPoints);
      this.examPoints = this.assignFixedValue(data.examPoints);
      this.cumExamPoints = this.assignFixedValue(data.cumExamPoints);
      
      if(data.lastUpdatedVideoPoints != null){
        this.lastUpdatedVideoPoints = this.getFormatedDate(data.lastUpdatedVideoPoints);
      }
      if(data.lastUpdatedExamPoints != null){
        this.lastUpdatedExamPoints = this.getFormatedDate(data.lastUpdatedExamPoints);
      }
      if(data.lastUpdatedCumExamPoints != null){
        this.lastUpdatedCumExamPoints = this.getFormatedDate(data.lastUpdatedCumExamPoints);
      }        
     });
  }

  getFormatedDate(date:string){
    var dateArr = new Date(Date.parse(date)).toString().split(" ");
    return dateArr[0] + ", " +dateArr[2] +" " + dateArr[1] +" "+ dateArr[3]; 
  }

  assignFixedValue(point:number){
    if(point === 0){
      return '0';
    }else{
      return point.toFixed(2);
    }
  }
}
