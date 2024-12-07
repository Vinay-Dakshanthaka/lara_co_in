import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ExamRecordService } from 'src/app/dashboard/services/exam-record.service';
import { ExamResult } from '../../classes/examResult';
import * as _ from 'underscore';

@Component({
  selector: 'app-cum-ques-details',
  templateUrl: './cum-ques-details.component.html',
  styleUrls: []
})
export class CumQuesDetailsComponent implements OnInit {
  public cumTestForm: FormGroup;
  examResult;//: Object = {};
  resultQuesIdsArr: Array<any> = [];
  attemptQAnsArr: Array<any> = [];
  optedAnsArr: Array<any> = [];
  totalAttemptedAns: number = 0;
  totalUnAttemptedAns: number = 0;
  totalCorrAns:number = 0;
  totalWrAns:number = 0;
  cumExamRecordId: number;  
  totalQuestions:number;
  constructor(private _fb: FormBuilder, private router: Router, private activatedRoute: ActivatedRoute,
    private examRecordService: ExamRecordService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.cumExamRecordId = +params['cumExamRecordId'];      
    });   

    this.cumTestForm = this._fb.group({
      data: this._fb.array([
      ])
    });
    this.getCumQuesAnsListByCexId(this.cumExamRecordId);
  }
  get data(){
    return this.cumTestForm.get('data') as FormArray;
  }

  getCumQuesAnsListByCexId(cumExamRecordId:number){
    this.examRecordService._getCumQuesAnsListByCexId(cumExamRecordId)
      .subscribe(data=>{
        let qaString = data.cumExamResultData.questionAnsData;
        this.attemptQAnsArr = qaString.split(",");     
        _.each(this.attemptQAnsArr, (aqr:any)=>{
          let splitArr = aqr.split("-");                
          this.optedAnsArr.push({
            questionId:parseInt(splitArr[0]),
            optedAnsId: splitArr[1]
          })
        }); 
        let questionsList = data.questionsList;        
        for(let i = 0; i <= questionsList.length-1; i++){
          questionsList[i].optedAns = this.optedAnsArr[i].optedAnsId;
        }
        this.examResult = data.cumExamResultData;
        this.totalQuestions = data.cumExamResultData.totalQuestions;
        this.setQuestion(questionsList);
      
        this.resultQuesIdsArr = _.map(questionsList, ((ques:any, key)=>{
          let className = "qtab-un";
          
          if(ques.optedAns == ""){
            this.totalUnAttemptedAns = this.totalUnAttemptedAns + 1;
            className = "qtab-un";
          }else if(ques.optedAns == ques.correctOptions[0].answerKey){
            this.totalCorrAns = this.totalCorrAns + 1;
            className = "qtab-cans";
          }else{
            this.totalWrAns = this.totalWrAns + 1;
            className = "qtab-wans";
          }         
          return {
            questionId: ques.questionId,
            questionRef: (++key),
            questionClass: className          
          }
        }));
      });
  }

  setQuestion(data: any, ) {
    let control = <FormArray>this.cumTestForm.controls.data;
    data.forEach((x: any) => {
      let isWrongAns = false;
      if(x.correctOptions[0].answerKey != x.optedAns){
        isWrongAns = true;
      }
      let correctAnswers = x.correctOptions[0].answerKey.split('').join(',');
      control.push(this._fb.group({
        questionId: x.questionId,
        questionDesc: x.questionDesc,
        options: this.setOptions(x),
        isWrongAns: isWrongAns,
        correctAnswers: correctAnswers        
      }))
    })
  }

  setOptions(x: any) {
    let arr = new FormArray([]);
    let cOptsArr = x.correctOptions[0].answerKey.split('');
    let optAnsArr = [];
    if(x.optedAns != ""){
      optAnsArr = x.optedAns.split('');
    }    
    x.options.forEach((y: any, index:any) => {
      let isCorrect = false;
      let isWrong = false;
      let isOpted = false;
      // if(cOptsArr.includes(y.optionAbbr)){
      //   isOpted = true;
      //   if(optAnsArr.includes(y.optionAbbr)){
      //     isCorrect = true;        
      //   }else{         
      //   }
      // }
      if(optAnsArr.includes(y.optionAbbr)){
             isOpted = true;  
             if(cOptsArr.includes(y.optionAbbr)) {
               isCorrect = true;
             }else{
               isCorrect = false;
               isWrong = true;
             }               

      }
      arr.push(this._fb.group({
        questionId: x.questionId,
        optionId: y.optionId,
        optionAbbr: y.optionAbbr,
        optionDesc: y.optionDesc,
        optionCheckedValue: isOpted,
        optionCorrect: isCorrect,
        optionWrong: isWrong
      }))
    })
    return arr;
  }
}


