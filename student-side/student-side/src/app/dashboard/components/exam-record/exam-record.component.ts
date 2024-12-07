import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ExamRecordService } from '../../services/exam-record.service';
import * as _ from 'underscore';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
@Component({
  selector: 'app-exam-record',
  templateUrl: './exam-record.component.html',
  styleUrls: ['./exam-record.component.css']
})
export class ExamRecordComponent implements OnInit {
  dueExRecordsList:any;  
  userId: string;
  noDueExam:boolean = false;
  constructor(private router:Router, private activatedRoute: ActivatedRoute,
    private examRecordService: ExamRecordService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.userId = this.tokenStorageService.getUserId();
    this.findAllExamRecords();
  }

  findAllExamRecords(){
    this.examRecordService._findAllExamRecords()
      .subscribe(data=>{              
        let dueExRecordsDetails = _.chain(data).groupBy((couresPackage:any)=>couresPackage.coursePackageId)
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
                              let dueDate:any = _.find(grpMicroAry, (obj:any)=> { return obj.dueDate });                              
                              let exRecId:any = _.find(grpMicroAry, (obj:any) => { return obj.examRecordId });
                              return {
                                microTopicId: k,
                                microTopicName: mct.microTopicName,                                                               
                                examRecordId: exRecId.examRecordId,
                                dueDate: (new Date(Date.parse(dueDate.dueDate)).toString().split(" "))[0] + ", " +
                                (new Date(Date.parse(dueDate.dueDate)).toString().split(" "))[2] + " " +
                                (new Date(Date.parse(dueDate.dueDate)).toString().split(" "))[1] + " " +
                                (new Date(Date.parse(dueDate.dueDate)).toString().split(" "))[3] 
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
          this.dueExRecordsList = dueExRecordsDetails;
          if(this.dueExRecordsList.length == 0){
            this.noDueExam = true;
          }
      
      });
  }
}
