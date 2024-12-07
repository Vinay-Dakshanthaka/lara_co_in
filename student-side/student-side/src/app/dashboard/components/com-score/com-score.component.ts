import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/app/course/services/course.service';
import { ExamRecordService } from '../../services/exam-record.service';
import * as _ from 'underscore';

@Component({
  selector: 'app-com-score',
  templateUrl: './com-score.component.html',
  styleUrls: []
})
export class ComScoreComponent implements OnInit {
  cumExamRecords: any;
  microTopics:any;
  noRecords = false;
  constructor(private fb: FormBuilder, private router: Router, private activatedRoute: ActivatedRoute,
    private courseService: CourseService, private examRecordService: ExamRecordService) { }

  ngOnInit() {
    this.findAllCumulativeExRecords();
  }

  findAllCumulativeExRecords(){
    this.examRecordService._findAllCumulativeExRecords().subscribe(data=>{
      let cumExamRecArr = _.chain(data).groupBy((couresPackage: any) => couresPackage.coursePackageId)
          .map((grpCpgArr, i) => {
            let cpgName: any = _.find(grpCpgArr, (obj: any) => { return obj.coursePackageName });            
            let courseName:any = _.find(grpCpgArr, (obj: any) => { return obj.courseName });           

            return {
              coursePackageId: i,
              coursePackageName: cpgName.coursePackageName,
              courses:_.chain(grpCpgArr).groupBy((courseObj:any)=>courseObj.courseId)
                .map((grpCourseArr, j)=>{
                  let csName:any = _.find(grpCourseArr, (obj:any)=>{ return obj.courseName});
                  return {
                    courseId: j,
                    courseName: csName.courseName,            
              compExams: _.chain(grpCpgArr).groupBy((compExObj: any) => compExObj.cumExamRecordId).map((compExamsArr, l) => {
                let compDateObj: any = _.find(compExamsArr, (obj: any) => { return obj.completedDate });                
                let marksObtObj: any = _.find(compExamsArr, (obj: any) => {
                   return (obj.marksObtained == 0 ? "0" : obj.marksObtained) 
                });
                let totMarksObj: any = _.find(compExamsArr, (obj: any) => { return obj.totalMarks });
                let qaObj: any = _.find(compExamsArr, (obj: any) => { return obj.questionAnsData });
                return {
                  cumExamRecordId: l,                  
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
            }
          }).value()
        this.cumExamRecords = cumExamRecArr;
        if(this.cumExamRecords.length == 0){
          this.noRecords = true;
        }
    });
  }  

  getMiTListByCumExRecId(cumExamRecordId:number){
    this.microTopics = [];
    this.examRecordService._getMiTListByCumExRecId(cumExamRecordId).subscribe(data=>{
      this.microTopics = data;
      document.getElementById("mctDetailBtn").click();
    })
  }
}
