import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ExamRecordService } from '../../services/exam-record.service';
import * as _ from 'underscore';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-score-detail',
  templateUrl: './score-detail.component.html',
  styleUrls: ['./score-detail.component.css']
})
export class ScoreDetailComponent implements OnInit {

  constructor(private _fb: FormBuilder, private router: Router, private activatedRoute: ActivatedRoute,
  private examRecordService: ExamRecordService) { }
  examRecordId: number;
  compExamRecords: any;
  public testForm: FormGroup;   
  objs:any;
  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(queryParams => {
      this.examRecordId = queryParams['exRecId'];
    });
    this.getAllCompExamRecords(this.examRecordId);
  }

  getAllCompExamRecords(exRecId: number) {
    this.examRecordService._getAllCompExamRecords(exRecId)
      .subscribe(data => {
        let a = {coursePackageId: data[0].coursePackageId,  
          coursePackageName: data[0].coursePackageName
        }   
        let cmpExRecArr = _.chain(data).groupBy((couresPackage: any) => couresPackage.coursePackageId)
          .map((grpCpgArr, i) => {
            let cpgName: any = _.find(grpCpgArr, (obj: any) => { return obj.coursePackageName });            
            let courseName:any = _.find(grpCpgArr, (obj: any) => { return obj.courseName });
            let topicName: any = _.find(grpCpgArr, (obj: any) => { return obj.topicName });
            let subTopicId: any = _.find(grpCpgArr, (obj: any) => { return obj.subTopicId });
            let subTopicName: any = _.find(grpCpgArr, (obj: any) => { return obj.subTopicName });
            let microTopicId: any = _.find(grpCpgArr, (obj: any) => { return obj.microTopicId });
            let microTopicName: any = _.find(grpCpgArr, (obj: any) => { return obj.microTopicName });

            return {
              coursePackageId: i,
              coursePackageName: cpgName.coursePackageName,
              courseName: courseName.courseName,
              topicName: topicName.topicName,
              subTopicId: subTopicId.subTopicId,
              subTopicName: subTopicName.subTopicName,
              microTopicId: microTopicId.microTopicId,
              microTopicName: microTopicName.microTopicName,

              compExams: _.chain(grpCpgArr).groupBy((compExObj: any) => compExObj.examCompRecordId).map((compExamsArr, l) => {
                let compDateObj: any = _.find(compExamsArr, (obj: any) => { return obj.completedDate });                
                let marksObtObj: any = _.find(compExamsArr, (obj: any) => {
                   return (obj.marksObtained == 0 ? "0" : obj.marksObtained) 
                });
                let totMarksObj: any = _.find(compExamsArr, (obj: any) => { return obj.totalMarks });
                let qaObj: any = _.find(compExamsArr, (obj: any) => { return obj.questionAnsData });
                return {
                  examCompRecordId: l,                  
                  marksObtained: marksObtObj.marksObtained,
                  totalMarks: totMarksObj.totalMarks,
                  questionAnsData: qaObj.questionAnsData,
                  completedDate: (new Date(Date.parse(compDateObj.completedDate)).toString().split(" "))[0] + ", " +
                                (new Date(Date.parse(compDateObj.completedDate)).toString().split(" "))[2] + " " +
                                (new Date(Date.parse(compDateObj.completedDate)).toString().split(" "))[1] + " " +
                                (new Date(Date.parse(compDateObj.completedDate)).toString().split(" "))[3] + ", " + 
                                (new Date(Date.parse(compDateObj.completedDate)).toString().split(" "))[4] 
                }
              }).value()
            }
          }).value()
        this.compExamRecords = cmpExRecArr;
      })
  }
  
}
