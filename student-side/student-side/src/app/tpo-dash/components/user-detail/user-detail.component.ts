import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TpoService } from '../../services/tpo.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: []
})
export class UserDetailComponent implements OnInit {
  userId:string;
  userDetails:any
  constructor(private router: Router, private activatedRoute:ActivatedRoute, private tpoService:TpoService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      //this.id = +params['id']; // (+) converts string 'id' to a number
      this.userId = params['userId'];
      this.getLearnerCourseDetails(this.userId);
    });    
  }

  getLearnerCourseDetails(userId:string){
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
  }

}
