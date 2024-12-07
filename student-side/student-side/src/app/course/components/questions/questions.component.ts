import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';
//import { Question } from 'src/app/Interface/question.interface';
import { Router, ActivatedRoute } from '@angular/router';
import * as _ from 'underscore';
import { ExamRecordService } from 'src/app/dashboard/services/exam-record.service';
import { ExamCompRecordClass } from '../../classes/examCompRecordClass';
import { Question } from '../../interfaces/question.interface';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { PlatformLocation } from '@angular/common';
declare var $: any;

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css']
})
export class QuestionsComponent {
  testScore: any;
  testScoreMsg: string;
  testStarted: boolean = false;
  examCompRecordClass: ExamCompRecordClass = new ExamCompRecordClass();
  userId: string;
  videoId: string;
  microTopicId: string;
  examRecordId: number;
  timerTime:string;
  marks:number;
  totalMarks: number; 
  stopWatchTime:any;
  unattQuesIdsArr:any;
  attQuesArr:any;
  questionIds:any;
  examCompRecordId: number;
  public testForm: FormGroup;
  coursePackDetails;//: Object = {};
  totalQuestions: number;
  distance: number;  
  
  constructor(private _fb: FormBuilder, private examRecordService: ExamRecordService, private router: Router,
    private activatedRoute: ActivatedRoute,
    private tokenStorageService: TokenStorageService,
    private location: PlatformLocation) { 
      
    }

  ngOnInit() {
    this.userId = this.tokenStorageService.getUserId();
    this.testStarted = false;
    this.activatedRoute.queryParams.subscribe(params => {
      this.videoId = params['videoId'];
      this.microTopicId = params['microTopicId'];
      this.examRecordId = +params['examRecordId'];
      this.examCompRecordClass.examRecordId = this.examRecordId;
      //this.examCompRecordClass.userId = this.userId;
    });
    
    this.testForm = this._fb.group({
      data: this._fb.array([
        //this.addformdata(),
      ])
    })

    this.getQuestions(this.videoId, this.microTopicId);
  }
  addButtonclick(): void {
    (<FormArray>this.testForm.get('data')).push(this.addformdata());
  }

  addformdata() {
    return this._fb.group({
      questionId: ['', [Validators.required, Validators.minLength(5)]],
      questionDesc: ['', [Validators.required, Validators.minLength(5)]],
      options: this._fb.array([
        this.initOptions(),
      ])
    })
  }

  initOptions() {
    return this._fb.group({
      id: ['', Validators.required],
      optionDesc: ['']
    });
  }

  setQuestion(data: any) {
    let control = <FormArray>this.testForm.controls.data;
    data.forEach((x: any) => {
      control.push(this._fb.group({
        questionId: x.questionId,
        questionDesc: x.questionDesc,
        options: this.setOptions(x)
      }))
    })
  }

  setOptions(x: any) {
    let arr = new FormArray([])
    x.options.forEach((y: any) => {
      arr.push(this._fb.group({
        questionId: x.questionId,
        optionId: y.optionId,
        optionAbbr: y.optionAbbr,
        optionDesc: y.optionDesc,
        optionCheckedValue: false
      }))
    })
    return arr;
  }

  /*save(model: Question) {
    let questionAnsArr = [];
    _.each(this.testForm.value.data,(ques:any)=>{
      let opts = [];
      _.find(ques.options, (option:any)=>{        
        if(option.optionCheckedValue == true) {
          opts.push({optionId:option.optionId, optionAbbr:option.optionAbbr});
        }
      });
      questionAnsArr.push({questionId:ques.questionId, options:opts})
    });    
  }*/

  save(model: Question) {
    clearInterval(this.stopWatchTime);
    let questionAnsArr = [];
    _.each(this.testForm.value.data, (ques: any) => {
      let opts = [];
      let optionStr = ques.questionId + "-";
      _.find(ques.options, (option: any) => {
        if (option.optionCheckedValue == true) {
          optionStr = optionStr + option.optionAbbr;
          opts.push({ optionId: option.optionId, optionAbbr: option.optionAbbr });
        }
      });
      questionAnsArr.push(optionStr);

    });
    let questionAnsString = questionAnsArr.join();
    this.examCompRecordClass.questionAnsData = questionAnsString;
    this.examCompRecordClass.totalMarks = this.totalQuestions;
    this.examCompRecordClass.totalQuestion = this.totalQuestions;
    this.examRecordService._createExamCompletedRecord(this.examCompRecordClass)
      .subscribe(data => {
        this.testScore = data;
        this.marks = (data.marksObtained == 0 ? "0" : data.marksObtained);
        this.totalMarks = data.totalMarks;
        this.testScoreMsg = "Your total score is " + data.marksObtained + " out of " + data.totalMarks;
        this.examCompRecordId = data.examCompRecordId;
        document.getElementById("openTestResultModalButton").click();
      },
        error => {
        });
  }

  startTest() {
    this.testStarted = true;
    this.startTimer();
  }

  reStartTest() {
    this.router.navigate(['/questions'], {
      queryParams: {
        videoId: this.testScore.videoId, microTopicId: this.testScore.microTopicId,
        userId: this.userId
      }
    });
    this.testStarted = false;
  }

  getQuestions(videoId: string, microTopicId: string) {    
    this.examRecordService._getQuestionsListForExam(videoId, microTopicId)
      .subscribe(data => {            
        this.totalQuestions = data.questionsList.length;
        this.unattQuesIdsArr = _.map(data.questionsList, ((ques:any, key)=>{
          return {
            questionId: ques.questionId,
            questionRef: (++key),
            questionClass: 'qtab-un',            
          }
        }));
        this.questionIds = this.unattQuesIdsArr;
        this.setQuestion(data.questionsList);
        this.coursePackDetails = data.coursePackDetails;
      });
  }

  startTimer() {
    var dt = new Date();
    var countDownDate = dt.setMinutes(dt.getMinutes() + this.totalQuestions);
    var countDownDateTime = dt.getTime();

    // Update the count down every 1 second
    this.stopWatchTime = setInterval(function () {

      // Get todays date and time
      var now = new Date().getTime();

      // Find the distance between now and the count down date
      this.distance = countDownDate - now;

      // Time calculations for days, hours, minutes and seconds
      var days = Math.floor(this.distance / (1000 * 60 * 60 * 24));
      var hours = Math.floor((this.distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      var minutes = Math.floor((this.distance % (1000 * 60 * 60)) / (1000 * 60));
      var seconds = Math.floor((this.distance % (1000 * 60)) / 1000);

      // Output the result in an element with id="demo"
      /*document.getElementById("demo").innerHTML = days + "d " + hours + "h "
      + minutes + "m " + seconds + "s ";*/      
      var timerClockDiv = document.getElementById("timerclock");
      if(timerClockDiv != null){
        document.getElementById("timerclock").innerHTML = minutes + "m " + ": " + seconds + "s ";
      }else{
        clearInterval(this.stopWatchTime);
      }

      // If the count down is over, write some text 
      if (this.distance < 0) {
        clearInterval(this.stopWatchTime);
        document.getElementById("timerclock").innerHTML = "Time UP!";        
      }
    }, 1000);
  }

  toggleQuestionData1(event:any){
    let questionId = event.target.getAttribute('questionId');
      if(event.target.checked){    
      $("#ref"+questionId).removeClass("qtab-un");
      $("#ref"+questionId).addClass("qtab-an");
    }else{
      // let quesObj = _.find(this.questionIds, (ques:any)=>{
      //   return (ques.questionId == parseInt(questionId));
      // });
     
    }    
  }

  toggleQuestionData(event:any){
    let questionId = event.target.getAttribute('questionId');
   
       let selectedQuestion = _.find(this.testForm.value.data, (ques:any)=>{
         return (ques.questionId == parseInt(questionId));
       });
      let opts = [];
      _.find(selectedQuestion.options, (option: any) => {
        if (option.optionCheckedValue == true) {    
          opts.push(option.optionId);
        }
      });
      if(opts.length == 1){            
        $("#qref"+questionId).removeClass("qtab-un");
        $("#qref"+questionId).addClass("qtab-an");      
    }else if(opts.length == 0){
      $("#qref"+questionId).removeClass("qtab-an");
      $("#qref"+questionId).addClass("qtab-un");     
    }    
  }
  
}
