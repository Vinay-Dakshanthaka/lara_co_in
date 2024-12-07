
import { Component, OnInit, SystemJsNgModuleLoader, HostListener, ViewChild, ElementRef } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';
//import { Question } from 'src/app/Interface/question.interface';
import { Router, ActivatedRoute, NavigationStart } from '@angular/router';
import * as _ from 'underscore';
import { ExamRecordService } from 'src/app/dashboard/services/exam-record.service';
import { ExamCompRecordClass } from '../../classes/examCompRecordClass';
import { Question } from '../../interfaces/question.interface';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { PlatformLocation } from '@angular/common';
import { CumulativeExamRecord } from 'src/app/dashboard/classes/cumulativeExamRecord';
import { LoaderService } from 'src/app/common-service/loader.service';
declare var $: any;

@Component({
  selector: 'app-cum-questions',
  templateUrl: './cum-questions.component.html',
  styleUrls: []
})
export class CumQuestionsComponent {
  @ViewChild('testResultModal', { static: true }) testResultModal: ElementRef;

  testScore: any;
  testScoreMsg: string;
  testStarted: boolean = false;
  cumExamRecord: CumulativeExamRecord = new CumulativeExamRecord();
  userId: string;
  microTopicIds: Array<any> = [];
  cumExamRecordId: number;
  coursePackageId: number;
  courseId: number;
  microTopics: any;
  timerTime: string;
  exError = false;
  exErrorMsg: string;
  marks: number;
  stopWatchTime: any;
  totalMarks: number;
  unattQuesIdsArr: any;
  attQuesArr: any;
  questionIds: any;
  public cumTestForm: FormGroup;
  coursePackDetails: Object = {};
  totalQuestions: number;
  distance: number;
  microTopicIdsStr: string;
  testResult: boolean

  constructor(private _fb: FormBuilder, private examRecordService: ExamRecordService, private router: Router,
    private activatedRoute: ActivatedRoute,
    private tokenStorageService: TokenStorageService,
    private location: PlatformLocation,
    private loaderService: LoaderService,
    private elementRef: ElementRef) {

  }
  /*@HostListener('window:unload', [ '$event' ])
  unloadHandler(event) {
   alert("bl");

  }

  @HostListener('window:beforeunload', [ '$event' ])
  beforeUnloadHander(event) {
   alert("ok");
   clearInterval(this.stopWatchTime);
  }
  */

  ngOnInit() {
    this.userId = this.tokenStorageService.getUserId();
    this.testStarted = false;
    this.activatedRoute.params.subscribe(params => {
      this.cumExamRecordId = +params['cumExamRecordId'];
      //this.examCompRecordClass.examRecordId = this.examRecordId;
      //this.examCompRecordClass.userId = this.userId;
    });
    this.getCumulativeExamRecord(this.cumExamRecordId);
    this.cumTestForm = this._fb.group({
      data: this._fb.array([
        //this.addformdata(),
      ])
    });
  }
  addButtonclick(): void {
    (<FormArray>this.cumTestForm.get('data')).push(this.addformdata());
  }

  getCumulativeExamRecord(cumExamRecordId: number) {
    this.examRecordService._getCumulativeExamRecord(cumExamRecordId).subscribe(data => {
      this.coursePackageId = data[0].coursePackageId;
      this.courseId = data[0].courseId;
      this.totalQuestions = data[0].totalQuestions;
      this.microTopicIdsStr = data[0].examRelatedInfo;
      this.getCumulativeQuestions(this.microTopicIdsStr, this.totalQuestions);
      this.getMiTListByCumExRecId(cumExamRecordId);
    })
  }

  getMiTListByCumExRecId(cumExamRecordId: number) {
    this.examRecordService._getMiTListByCumExRecId(cumExamRecordId).subscribe(data => {
      this.microTopics = [];
      this.microTopics = data;
    });
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
    let control = <FormArray>this.cumTestForm.controls.data;
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
    _.each(this.cumTestForm.value.data,(ques:any)=>{
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
    this.loaderService.show();
    clearInterval(this.stopWatchTime);
    let questionAnsArr = [];
    _.each(this.cumTestForm.value.data, (ques: any) => {
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
    this.cumExamRecord.coursePackageId = this.coursePackageId;
    this.cumExamRecord.courseId = this.courseId;
    this.cumExamRecord.examRelatedInfo = this.microTopicIdsStr;
    this.cumExamRecord.cumExamRecordId = this.cumExamRecordId;
    this.cumExamRecord.questionAnsData = questionAnsString;
    this.cumExamRecord.totalMarks = this.totalQuestions;
    this.cumExamRecord.totalQuestions = this.totalQuestions;
    this.examRecordService._updateCumulativeExamRecord(this.cumExamRecord)
      .subscribe(data => {
        this.testStarted = false
        this.loaderService.hide();
        this.testScore = data;
        this.marks = (data.marksObtained == 0 ? "0" : data.marksObtained);
        this.totalMarks = data.totalMarks;
        this.testScoreMsg = "Your total score is " + data.marksObtained + " out of " + data.totalMarks;
        this.cumExamRecordId = data.cumExamRecordId;
        this.testResult = true        
      },
        error => {
        });
  }

  startTest() {
    this.testResult = false
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

  getCumulativeQuestions(microTopicIdsStr: string, totalQuestions: number) {
    this.examRecordService._getCumQuestionsListForExam(microTopicIdsStr, totalQuestions)
      .subscribe(data => {
        if (data.exceptionReport != null) {
          this.exError = true;
          this.exErrorMsg = data.exceptionReport.exception.message;
        } else {
          this.totalQuestions = data.questionsList.length;
          this.unattQuesIdsArr = _.map(data.questionsList, ((ques: any, key) => {
            return {
              questionId: ques.questionId,
              questionRef: (++key),
              questionClass: 'qtab-un',
            }
          }));
          this.questionIds = this.unattQuesIdsArr;
          this.setQuestion(data.questionsList);
          this.coursePackDetails = data.coursePackDetails;
        }
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
      if (timerClockDiv != null) {
        document.getElementById("timerclock").innerHTML = minutes + "m " + ": " + seconds + "s ";
      } else {
        clearInterval(this.stopWatchTime);
      }


      // If the count down is over, write some text 
      if (this.distance < 0) {
        clearInterval(this.stopWatchTime);
        document.getElementById("timerclock").innerHTML = "Time UP!";
      }
    }, 1000);
  }

  toggleQuestionData1(event: any) {
    let questionId = event.target.getAttribute('questionId');
    if (event.target.checked) {
      $("#ref" + questionId).removeClass("qtab-un");
      $("#ref" + questionId).addClass("qtab-an");
    } else {
      // let quesObj = _.find(this.questionIds, (ques:any)=>{
      //   return (ques.questionId == parseInt(questionId));
      // });

    }
  }

  toggleQuestionData(event: any) {
    let questionId = event.target.getAttribute('questionId');

    let selectedQuestion = _.find(this.cumTestForm.value.data, (ques: any) => {
      return (ques.questionId == parseInt(questionId));
    });
    let opts = [];
    _.find(selectedQuestion.options, (option: any) => {
      if (option.optionCheckedValue == true) {
        opts.push(option.optionId);
      }
    });
    if (opts.length == 1) {
      $("#qref" + questionId).removeClass("qtab-un");
      $("#qref" + questionId).addClass("qtab-an");
    } else if (opts.length == 0) {
      $("#qref" + questionId).removeClass("qtab-an");
      $("#qref" + questionId).addClass("qtab-un");
    }
  }

}

