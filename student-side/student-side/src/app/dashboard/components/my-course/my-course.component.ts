import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserProfileService } from '../../services/user-profile.service';
import * as _ from 'underscore';

@Component({
  selector: 'app-my-course',
  templateUrl: './my-course.component.html',
  styleUrls: ['./my-course.component.css']
})
export class MyCourseComponent implements OnInit {
  userId: string;
  coursePackageDetails: any
  noCoursePackage: boolean = false;
  currentCourseName: string;
  constructor(private router: Router, private activatedRoute: ActivatedRoute,
    private userProfileService: UserProfileService) { }

  ngOnInit() {
    // this.activatedRoute.queryParams.subscribe(queryParams=>{
    //   this.userId = queryParams['userId'];
    // });
    this.userId = window.sessionStorage.getItem('UserId');

    this.getAllCoursePackForLearner();
  }

  getAllCoursePackForLearner() {
    this.userProfileService._getAllCoursePackForLearner()
      .subscribe(data => {
        /*var x =  _.chain(data)
             .groupBy('coursePackageId')
             .map(function(value, key) {
                 return {
                     coursePackageId: key,
                     courses: _.pluck(value, 'courseId')
                 }
             }).value();
           */
        console.log(data)
        let cpgDetails = _.chain(data).groupBy((couresPackage: any) => couresPackage.coursePackageId)
          .map((grpCpgArr, i) => {
            let cpgName: any = _.find(grpCpgArr, (obj: any) => { return obj.coursePackageName });
            return {
              coursePackageId: i,
              coursePackageName: cpgName.coursePackageName,
              courses: _.chain(grpCpgArr).groupBy((courseObj: any) => courseObj.courseId)
                .map((grpCourseArr, j) => {
                  let csName: any = _.find(grpCourseArr, (obj: any) => { return obj.courseName });
                  let csThumb: any = _.find(grpCourseArr, (obj: any) => { return obj.courseThumbnail });
                  let isAct: any = _.find(grpCourseArr, (obj: any) => { return obj.isActive });
                  return {
                    courseId: j,
                    courseName: csName.courseName,
                    courseThumbnail: csThumb.courseThumbnail,
                    isActive: isAct.isActive
                  }
                }).value(),
            }
          }).value()
        this.coursePackageDetails = cpgDetails;
        if (this.coursePackageDetails.length == 0) {
          this.noCoursePackage = true;
        }
      });
  }

  showCoursePopup(courseName: string) {
    this.currentCourseName = courseName;
    document.getElementById("comingSoonBtn-cs").click();
  }

}
