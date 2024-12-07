import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ExamRecordService } from 'src/app/dashboard/services/exam-record.service';
import { ExamResult } from '../../classes/examResult';
import * as _ from 'underscore';

@Component({
  selector: 'app-ques-detail',
  templateUrl: './ques-detail.component.html',
  styleUrls: ['./ques-detail.component.css']
})
export class QuesDetailComponent implements OnInit {
  public testForm: FormGroup;
  examResult;//: Object = {};
  resultQuesIdsArr: Array<any> = [];
  attemptQAnsArr: Array<any> = [];
  optedAnsArr: Array<any> = [];
  totalAttemptedAns: number = 0;
  totalUnAttemptedAns: number = 0;
  totalCorrAns:number = 0;
  totalWrAns:number = 0;
  cexamRecordId: number;  
  exRecId: number;
  totalQuestions:number;
  constructor(private _fb: FormBuilder, private router: Router, private activatedRoute: ActivatedRoute,
    private examRecordService: ExamRecordService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.cexamRecordId = +params['cexId'];      
    });   
    this.activatedRoute.queryParams.subscribe(queryParams => {
      this.exRecId = +queryParams['exRecId'];      
    });

    this.testForm = this._fb.group({
      data: this._fb.array([
      ])
      //data: new FormControl([])

    });
    this.getQuesAnsListByCexId(this.cexamRecordId);
  }
  get data(){
    return this.testForm.get('data') as FormArray;
  }

  getQuesAnsListByCexId(cexamRecordId:number){
    this.examRecordService._getQuesAnsListByCexId(cexamRecordId)
      .subscribe(data=>{
        let qaString = data.examResultData.questionAnsData;
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
        this.examResult = data.examResultData;
        this.totalQuestions = data.examResultData.totalQuestions;
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
    let control = <FormArray>this.testForm.controls.data;
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
