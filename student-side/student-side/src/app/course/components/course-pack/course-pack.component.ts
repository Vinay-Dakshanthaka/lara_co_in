import { Component, OnInit } from '@angular/core';
import { CourseService } from './../../services/course.service';

@Component({
  selector: 'app-course-pack',
  templateUrl: './course-pack.component.html',
  styleUrls: ['./course-pack.component.css']
})
export class CoursePackComponent implements OnInit{
	coursePackages: Array<any>
	constructor( private courseService:CourseService){}
	
	ngOnInit(){

	}


	getCoursePackageList(){
     this.courseService._getCoursePackageList()
      .subscribe(data=>{
         this.coursePackages = data.coursePackageList;  
      }, error=>{
      });
  	}
}
