import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { UserService } from 'src/app/user/services/user.service';
import { TpoService } from '../../services/tpo.service';

@Component({
  selector: 'app-clg-users',
  templateUrl: './clg-users.component.html',
  styleUrls: []
})
export class ClgUsersComponent implements OnInit {
  usersList: any;
  collegeName: string;
  userListSection:boolean = true;
  userId:string;
  userDetails:any = {};
  userDetailsSection:boolean = false;
  courseStatsSection:boolean = false;
  infoSection:boolean = true;
  learnerCourseStatsList: Array<any> = [];
  learnerAvgMarksList: Array<any> = [];
  constructor(private router:Router, private activatedRoute: ActivatedRoute,
    private authService:AuthService, private userService:UserService,
    private topkenStorageServeice: TokenStorageService, private tpoService:TpoService) {

  }

  ngOnInit() {
    this.getAllUsersByCollegeId();
  }

  getAllUsersByCollegeId(){
    this.userService._getAllUsersByCollegeId().subscribe(data=>{
      this.usersList = data.userDetails;
      this.collegeName = data.collegeName;
    });
  }

  switchAction(tab:String){
    if(tab === 'infoSection'){
      this.infoSection = true;
      this.courseStatsSection = false;
    }else if(tab === 'courseStatsSection'){
      this.courseStatsSection = true;
      this.infoSection = false;
    }else if(tab === 'userListSection'){
      this.userListSection = true;
      this.userDetailsSection = false;
    }
  }
  getLearnerCourseDetails(userId:string){
    this.userListSection = false;
    this.userDetailsSection = true;
    this.infoSection = true;
    this.courseStatsSection = false;
    this.tpoService._getLearnerCourseDetails(userId).subscribe(data=>{
      this.userDetails = data;
      this.userDetails.learnerCoursePackList = this.userDetails.learnerCoursePackList.map((dt:any)=>{
        let dateArr = new Date(Date.parse(dt.enrollmentDate)).toString().split(" ");
        return {coursePackageId: dt.coursePackageId,
          coursePackageName: dt.coursePackageName,  
          enrollmentDate: dateArr[0] + ", "+dateArr[2] + " " +dateArr[1] + " " +dateArr[3]
        }
      })
    });    
    this.getLearnerCourseStats(userId);
  }

  getLearnerCourseStats(userId:string){
    this.tpoService._getLearnerCourseStats(userId).subscribe(data=>{
      console.log(data);
      //this.learnerCourseStatsList = data.learnerCourseStatsList;
      this.learnerAvgMarksList = data.learnerAvgMarksList;
      for(let i =0; i< data.learnerCourseStatsList.length; i++){
        this.learnerCourseStatsList.push({
          'totalVideosWatched': data.learnerCourseStatsList[i].totalVideosWatched,
          'totalExamsAttended': data.learnerCourseStatsList[i].totalExamsAttended,
          'totalAverageMarks': data.learnerAvgMarksList[i] == null ? 0 : data.learnerAvgMarksList[i].totalAverageMarks,
          'totalMarks': data.learnerAvgMarksList[i] == null ? 'NA' : data.learnerAvgMarksList[i].totalMarks,
          'courseId': data.learnerCourseStatsList[i].courseId,
          'totalExamsDue': data.learnerCourseStatsList[i].totalExamsDue,
          'courseName': data.learnerCourseStatsList[i].courseName,
          'coursePackageId': data.learnerCourseStatsList[i].coursePackageId,  
          'coursePackageName': data.learnerCourseStatsList[i].coursePackageName
        }); 
      }
       
    });
  }

}
