import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { Router, ActivatedRoute } from '@angular/router';
import * as _ from 'underscore';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { AuthLoginInfo } from 'src/app/auth/login-info';
declare var $:any;

@Component({
  selector: 'app-course-pack-detail',
  templateUrl: './course-pack-detail.component.html',
  styleUrls: ['./course-pack-detail.component.css']
})
export class CoursePackDetailComponent implements OnInit {
  coursePackageId: number;
  coursePackageDetails;//:any[] = [];
  noOfCourses: number;
  isSubscribed = false;
  info:any;
  role:string;
  currentCourseName: string;
  constructor(private courseService: CourseService, 
    private router: Router, 
    private activatedRoute: ActivatedRoute,
    private tokenService: TokenStorageService
    ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {      
      this.coursePackageId = +params['cpackId'];
    });

    let token = this.tokenService.getToken();
		
		this.info = {
			token: token,
			username: this.tokenService.getUsername(),
			authorities: this.tokenService.getAuthorities()
    };
    console.log('authorityInfo:');
    console.log(this.info,AuthLoginInfo);
		if(this.info.authorities.length >0){
			this.role = this.info.authorities[0];
		}

    if(this.tokenService.getUserId() != null){
      this.courseService._checkCoursePackageSubscription(this.coursePackageId)
        .subscribe(data=>{
          this.isSubscribed = data;
        });
    }
    this.checkSubscription(this.coursePackageId);
    this.getCoursePackage(this.coursePackageId);
    //$("#back-to-top").click();
    
  }

  checkSubscription(coursePackageId:number){
    this.courseService._checkCoursePackageSubscription(coursePackageId).subscribe(data=>{
      if(data){
        this.isSubscribed = true;
      }
    })
  }

  getCoursePackage(coursePackageId:number){
    this.courseService._getCoursePackage(coursePackageId)
      .subscribe(data=>{
        this.coursePackageDetails = data;
        this.noOfCourses = data.coursesUnderPackageList.length;
      }, error=>{
      })
  }

  showCoursePopup(courseName:string){
    this.currentCourseName  =  courseName;
    document.getElementById("comingSoonBtn").click();
  }

  // buyThisPackageNow(coursePackageId, promoCode){
  //   this.router.navigate()
  // }


}
