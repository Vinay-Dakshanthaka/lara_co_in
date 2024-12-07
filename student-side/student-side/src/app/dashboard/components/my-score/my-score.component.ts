import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ExamRecordService } from '../../services/exam-record.service';
import * as _ from 'underscore';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-my-score',
  templateUrl: './my-score.component.html',
  styleUrls: ['./my-score.component.css']
})
export class MyScoreComponent implements OnInit {
  userId: string;
  completedExamDetails: any;
  noScore:boolean = false;
  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private examRecordService: ExamRecordService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.userId = this.tokenStorageService.getUserId();
    this.getAllExamScoreByLrId();
  }

  getAllExamScoreByLrId() {
    this.examRecordService._getGrpExamScoreByLrId()
      .subscribe(data => {      
        // let cxDetails = _.chain(data).groupBy((topic:any) => topic.topicId).map((grpTopAry, i) => {
        //   let topName:any = _.find(grpTopAry, (obj:any) => { return obj.topicName });
        //   return {
        //     topicId: i,
        //     topicName: topName.topicName,
        //     subTopics: _.chain(grpTopAry).groupBy((subObj:any) => subObj.subTopicId).map(function (grpSubAry, j) {
        //       let subtopName:any = _.find(grpSubAry, (obj:any) => { return obj.subTopicName });
        //       return {
        //         subTopicId: j,
        //         subTopicName: subtopName.subTopicName,
        //         microTopics: _.chain(grpSubAry).groupBy((microObj:any) => microObj.microTopicId).map((grpMicroAry, k) => {
        //           let microTopName:any = _.find(grpMicroAry, (obj:any) => { return obj.microTopicName });
        //           return {
        //             microTopicId: k,
        //             microTopicName: microTopName.microTopicName,
        //           }
        //         }).value()
        //       }
        //     }).value(),
        //   }
        // }).value();
        //.map((subTopicId, topicid) => ({ return {topicId:topicid,}/*topicId, subTopicId*/ }))

        let cxDetails = _.chain(data).groupBy((couresPackage:any)=>couresPackage.coursePackageId)
          .map((grpCpgArr, i)=>{
            let cpgName:any = _.find(grpCpgArr, (obj:any)=>{ return obj.coursePackageName});
            return {
              coursePackageId: i,
              coursePackageName: cpgName.coursePackageName,
              courses: _.chain(grpCpgArr).groupBy((courseObj:any)=>courseObj.courseId)
                .map((grpCourseArr, j)=>{
                  let csName:any = _.find(grpCourseArr, (obj:any)=>{ return obj.courseName});
                  return {
                    courseId: j,
                    courseName: csName.courseName,
                    topics:_.chain(grpCourseArr).groupBy((topic:any) => topic.topicId).map((grpTopAry, i) => {
                      let topName:any = _.find(grpTopAry, (obj:any) => { return obj.topicName });
                      return {
                        topicId: i,
                        topicName: topName.topicName,
                        subTopics: _.chain(grpTopAry).groupBy((subObj:any) => subObj.subTopicId).map(function (grpSubAry, j) {
                          let subtopName:any = _.find(grpSubAry, (obj:any) => { return obj.subTopicName });
                          return {
                            subTopicId: j,
                            subTopicName: subtopName.subTopicName,
                            microTopics: _.chain(grpSubAry).groupBy((microObj:any) => microObj.microTopicId).map((grpMicroAry, k) => {
                              let mct:any = _.find(grpMicroAry, (obj:any) => { return obj.microTopicName });
                              let totalAttempts:any = _.find(grpMicroAry, (obj:any) => { return obj.totalAttempts });
                              let marksObtained:any = _.find(grpMicroAry, (obj:any) => { return obj.marksObtained });
                              let totalMarks:any = _.find(grpMicroAry, (obj:any) => { return obj.totalMarks });
                              let exRecId:any = _.find(grpMicroAry, (obj:any) => { return obj.examRecordId });
                              return {
                                microTopicId: k,
                                microTopicName: mct.microTopicName,                                
                                totalAttempts: totalAttempts.totalAttempts,
                                marksObtained:marksObtained == null ? 0 : marksObtained.marksObtained,
                               
                                totalMarks: totalMarks.totalMarks,
                                examRecordId: exRecId.examRecordId                      
                              }
                            }).value()
                          }
                        }).value(),
                      }
                    }).value(),
                  }
                }).value(),
            }
          }).value()
        this.completedExamDetails = cxDetails;
        if(this.completedExamDetails.length == 0){
          this.noScore = true;
        }

      })
  }


}
