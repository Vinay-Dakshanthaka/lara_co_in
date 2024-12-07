import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from 'src/app/course/services/course.service';
declare var $: any;
import * as _ from 'underscore';
import { ExamRecordService } from '../../services/exam-record.service';
import { CumulativeExamRecord } from '../../classes/cumulativeExamRecord';
import { LoaderService } from 'src/app/common-service/loader.service';

@Component({
  selector: 'app-com-exam',
  templateUrl: './com-exam.component.html',
  styleUrls: []
})
export class ComExamComponent implements OnInit {
  testSelectionForm: FormGroup;
  coursePackageList: Array<any> = [];
  courseList: Array<any> = [];
  topicDetails: Array<any> = [];
  subTopicDetails: Array<any> = [];
  watchedMicroTopics: any;
  formErr: Boolean = false;
  formErrMsg: string;
  dateRange = false;
  questionsCount = [];
  cumExamRecord: CumulativeExamRecord = new CumulativeExamRecord();
  microTopicFlag = false;
  datePickerOptions = {
    dateFormat: "yy-mm-dd",
    changeMonth: true,
    changeYear: true,
    yearRange: "2017:+nn",
  };

  constructor(private fb: FormBuilder, private router: Router, private activatedRoute: ActivatedRoute,
    private courseService: CourseService, private examRecordService: ExamRecordService, public loaderService: LoaderService) { }

  ngOnInit() {
    $(document).ready(function () {
      $('.datepicker').datepicker(this.datePickerOptions);
    });
    this.initTestSelectionFormData();

    this.getCoursePackages();
    $(window).scroll(function () {
      if ($(this).scrollTop() > 50) {
        $('#back-to-top').fadeIn();
      } else {
        $('#back-to-top').fadeOut();
      }
    });
    // scroll body to 0px on click
    $('#back-to-top').click(function () {
      $('#back-to-top').tooltip('hide');
      $('body,html').animate({
        scrollTop: 0
      }, 800);
      return false;
    });
  }

  initTestSelectionFormData() {
    this.testSelectionForm = this.fb.group({
      coursePackageId: ['', Validators.required],
      courseId: ['', Validators.required],
      totalQuestions: ['', Validators.required],
      subTopicsData: this.fb.array([
        this.initSubTopics(),
      ])
    });
  }

  initSubTopics() {
    return this.fb.group({
      subTopicId: [''],
      subTopicName: [''],
      subTopicCheckedValue: [''],
      microTopicsData: this.fb.array([])
    });
  }

  showDateRange() {
    this.dateRange = true;
  }

  submitTestSelectionForm() {
    if (this.testSelectionForm.value.totalQuestions == "") {
      this.formErr = true;
      this.formErrMsg = "Please Select Total Questions";
      return;
    }
    if (this.testSelectionForm.value.coursePackageId == "") {
      this.formErr = true;
      this.formErrMsg = "Please Select Course Package";
      return;
    }
    if (this.testSelectionForm.value.courseId == "") {
      this.formErr = true;
      this.formErrMsg = "Please Select Course";
      return;
    }
    //this.loaderService.show();
    let microTopicsArr = [];
    $("input.mctp:checkbox").each(function () {
      var $this = $(this);

      if ($this.is(":checked")) {
        if ($this.attr("microTopicId") != null) {
          microTopicsArr.push($this.attr("microTopicId"));
        }
      }
    });
    if (microTopicsArr.length === 0) {
      this.formErr = true;
      this.formErrMsg = "Please Select Micro Topic";
      return;
    }

    let selectedQuestionsCount = 0;

    for(var i = 0; i < microTopicsArr.length; i++){
      for(var j = 0; j < this.questionsCount.length; j++){
        if(microTopicsArr[i] == this.questionsCount[j].microTopicId){
          selectedQuestionsCount += this.questionsCount[j].questionsCount;
        }
      }
    }
    if (selectedQuestionsCount < this.testSelectionForm.value.totalQuestions) {
      this.formErr = true;
      this.formErrMsg = "Please enter lesser than " + selectedQuestionsCount;
      return;
    }


    /*_.each(this.testSelectionForm.value.subTopicsData, (st: any) => {
      _.find(st.microTopicsData, (mt: any) => {
        if (mt.microTopicCheckedValue == true) {
         microTopicsArr.push(mt.microTopicId);
        }
      });
    });*/
    let cumExamRecordId = 0;
    let totalQuestions = parseInt(this.testSelectionForm.value.totalQuestions);
    let coursePackageId = parseInt(this.testSelectionForm.value.coursePackageId);
    let courseId = parseInt(this.testSelectionForm.value.courseId);
    let questionAnsData = '';
    let totalMarks = totalQuestions;
    let marksObtained = 0;
    let percentMarks = 0;
    let examRelatedInfo = microTopicsArr.join();

    this.cumExamRecord.coursePackageId = coursePackageId;
    this.cumExamRecord.courseId = courseId;
    this.cumExamRecord.cumExamRecordId = cumExamRecordId;
    this.cumExamRecord.totalQuestions = totalQuestions;
    this.cumExamRecord.questionAnsData = questionAnsData;
    this.cumExamRecord.totalMarks = totalMarks;
    this.cumExamRecord.percentMarks = percentMarks;
    this.cumExamRecord.marksObtained = marksObtained;
    this.cumExamRecord.examRelatedInfo = examRelatedInfo;
    this.examRecordService._saveCumulativeExamRecord(this.cumExamRecord).subscribe(data => {
      this.loaderService.hide();
      if (data.exceptionReport == null) {
        let cumExamRecordId = data.cumExamRecordId;
        this.router.navigate(['/cumquestions', cumExamRecordId]);
      }
    }, error=>{
      console.log(error);
    });

  }

  setSubTopics(data: any) {
    let control = <FormArray>this.testSelectionForm.controls.subTopicsData;
    data.forEach((x: any) => {
      control.push(this.fb.group({
        subTopicId: x.subTopicId,
        subTopicName: x.subTopicName,
        subTopicCheckedValue: false,
        microTopicsData: this.setMicroTopics(x)
      }))
    })
  }

  setMicroTopics(x: any) {
    let arr = new FormArray([])
    x.microTopic.forEach((y: any) => {
      let isWatchedFlag = false;
      if ((this.watchedMicroTopics).includes(y.microTopicId)) {
        isWatchedFlag = true;
      }
      arr.push(this.fb.group({
        microTopicId: y.microTopicId,
        microTopicName: y.microTopicName,
        microTopicCheckedValue: false,
        isWatchedFlag: isWatchedFlag
      }))
    })
    return arr;
  }

  getCoursePackages() {
    this.courseService._getCoursePackageList().subscribe(data => {
      this.coursePackageList = data.coursePackageList;
    })
  }

  getCourseListByCpkgId(cpkgId: number) {
    this.examRecordService._findWatchedMicroTopicIds(cpkgId).subscribe(data => {
      this.watchedMicroTopics = data;
    });
    this.courseList = [];
    this.courseService._getCourseListByCoursePkgId(cpkgId).subscribe(data => {
      this.courseList = data.courseList;
    });
  }

  findAllTopicsByCourseId(courseId: number) {
    console.log("findAllTopicsByCourseId-1")
    this.findAllQuestionsCountByCourseId(courseId);
    console.log(" findAllTopicsByCourseId -2");
    this.topicDetails = [];
    this.subTopicDetails = [];
    this.setSubTopics(this.subTopicDetails);
    this.courseService._findAllTopicsByCourseId(courseId)
      .subscribe(data => {
        if (data.topicsData.length > 0) {
          this.testSelectionForm = this.fb.group({
            coursePackageId: this.testSelectionForm.value.coursePackageId,
            courseId: this.testSelectionForm.value.courseId,
            totalQuestions: this.testSelectionForm.value.totalQuestions,
            subTopicsData: this.fb.array([
              this.initSubTopics(),
            ])
          });
          this.microTopicFlag = true;
          this.topicDetails = data.topicsData;
          this.subTopicDetails = this.topicDetails[0].subTopic;
          this.setSubTopics(this.subTopicDetails);
        }
      }, error => {

      })
  }

  
  findAllQuestionsCountByCourseId(courseId: number) {
    console.log("findAllQuestionsCountByCourseId:");
    this.courseService._findAllQuestionsCountByCourseId(courseId)
      .subscribe(data => {
        this.questionsCount = data;
        console.log(this.questionsCount);
      },
      error =>{

      })
    }



  mtpAction(event: any) {

    let subTopicId = event.target.attributes.subtopicid.value;
    if (event.target.checked) {
      $("[dataSubTopicId=" + subTopicId + "]").prop('checked', true);
      //_.map(_.find(this.testSelectionForm.value.subTopicsData, (st:any)=>{ return st.subTopicId  == subTopicId; }), (mt:any)=>{ return mt.microTopicCheckedValue = true; });
    } else {
      $("[dataSubTopicId=" + subTopicId + "]").prop('checked', false);
    }
  }

  stpAction(event: any) {
    let dataSubTopicId = event.target.attributes.datasubtopicid.value;
    if (event.target.checked) {
      if ($("[dataSubTopicId=" + dataSubTopicId + "]").length === $("[dataSubTopicId=" + dataSubTopicId + "]:checked").length) {
        $("[subTopicId=" + dataSubTopicId + "]").prop('checked', true);
      }
    } else {
      //if($("[dataSubTopicId="+dataSubTopicId+"]:checked").length == 0){
      $("[subTopicId=" + dataSubTopicId + "]").prop('checked', false);
      //}      
    }
  }

}
