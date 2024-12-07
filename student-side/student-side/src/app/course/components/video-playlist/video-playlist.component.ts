import { HostListener, Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import { Component, OnInit } from '@angular/core';
import { VgAPI } from 'videogular2/core';
import { UserProfileService } from 'src/app/dashboard/services/user-profile.service';
import { VideoTutorialService } from '../../services/video-tutorial.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { ConstantsService } from 'src/app/shared/services/constants.service';

import * as _ from 'underscore';
import { TimeSpentOnVideo } from '../../classes/timeSpentOnVideo';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ExamRecordService } from 'src/app/dashboard/services/exam-record.service';
import { saveAs } from 'file-saver';
declare var $:any;

@Pipe({ name: 'safe' })
@Component({
  selector: 'app-video-playlist',
  templateUrl: './video-playlist.component.html',
  styleUrls: ['./video-playlist.component.css']
})
export class VideoPlaylistComponent implements OnInit {
  playlist: Array<any> = [
    {
      title: 'Getting Started',
      src: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/mt1-setup.mp4',
      type: 'video/mp4',
      microTopicId: '1',
      videoId: '1',
      microTopicName: 'What is Java'
    }
  ];

  currentIndex = 0;
  currentItem: any;
  api: VgAPI;
  viewCourseContent: Boolean = false;
  courseId: number;
  coursePackageId: number;
  courseDetails;//: Array<any> = [];
  topicDetails;//: Array<any> = [];
  subTopicDetails;//: Array<any> = [];
  mctName: string;
  currentVideoId: string;
  currentMicroTopicId: string;
  timeSpentOnVideo: TimeSpentOnVideo;
  userId: string;
  examRecordId: number;
  courseContent: Object = {};
  isSubscribed: Boolean = false;
  noVideoMsg: string;
  courseContentUrl: string;
  pdfUrl: SafeResourceUrl;
  courseMaterialUrl: SafeResourceUrl;
  videoSearchForm: FormGroup;
  noVideoFlag: Boolean = false;
  noVideoTitle: string;
  watchedMicroTopics:Array<any> = [];
  materialsList = [];
  constructor(private userProfieService: UserProfileService,
    private vdTutorialService: VideoTutorialService,
    private router: Router, private activatedRoute: ActivatedRoute,
    private courseService: CourseService,
    private tokenStorageService: TokenStorageService,
    private formBuilder: FormBuilder,
    public sanitizer: DomSanitizer,
    private examRecordService: ExamRecordService) {      
    }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.courseId = params['courseId'];
      this.coursePackageId = params['coursePackageId'];
    });

    this.videoSearchForm = this.formBuilder.group({
			otp: ['microTopicName']
    });
    

    this.userId = this.tokenStorageService.getUserId();
    this.currentItem = this.playlist[this.currentIndex];
    this.getSubscriptionStatus(this.coursePackageId);
    this.findAllVideosByCourseId(this.coursePackageId, this.courseId);
    this.findAllMaterialsByCourseId(this.courseId);
    this.findWatchedMicroTopicsByCourseId(this.courseId);
    this.getCourseDetailsByCourseId(this.courseId);    

  }

  
  @HostListener('window:keyup', ['$event'])
  keyEvent(event: KeyboardEvent) {
    console.log(event);

    if (event.key === 'ArrowRight') {
      
    }

    if (event.key === 'ArrowLeft') {
      
    }
  }



  onPlayerReady(api: VgAPI) {
    this.api = api;

    //this.api.getDefaultMedia().subscriptions.loadedMetadata.subscribe(this.playVideo.bind(this));
    //this.api.getDefaultMedia().subscriptions.ended.subscribe(this.nextVideo.bind(this));
    this.api.getDefaultMedia().subscriptions.ended.subscribe(this.vidEnded.bind(this));
  }

  nextVideo() {
    this.currentIndex++;

    if (this.currentIndex === this.playlist.length) {
      this.currentIndex = 0;
    }

    this.currentItem = this.playlist[this.currentIndex];
  }

  playVideo() {    
    let microTopicId = "#mct"+this.currentItem.microTopicId;  
      let microTopicIconId = "#mctIcon"+this.currentItem.microTopicId;  
      $('.mcts').removeClass("currVideo");  
      $(microTopicIconId).addClass("currVideo");   
      $(microTopicId).addClass("currVideo");
    this.api.play();
  }

  onClickPlaylistItem(item: any, index: number) {
    this.api.getDefaultMedia().subscriptions.loadedMetadata.subscribe(this.playVideo.bind(this));
    if (item != null) {
      this.currentIndex = index;
      var videoItem = _.find(this.playlist, (microT => {
        return microT.microTopicId == item.microTopicId;
      }));
      this.currentItem = videoItem;      
    } else {
      //if (this.isSubscribed) {
        this.noVideoTitle = "Video unavailable."
        this.noVideoMsg = "Video will be available soon.";
        this.noVideoFlag = true;
        document.getElementById("noVideoAvl").click();
      //} 
      // else {
      //   this.noVideoTitle = "Package Subscription required."
      //   this.noVideoMsg = "Please subscribe to this course package to see more videos.";
      //   document.getElementById("noVideoAvl").click();
      // }      
    }
  }

  findWatchedMicroTopicsByCourseId(coursePackageId:number){
    this.examRecordService._findWatchedMicroTopicIds(coursePackageId).subscribe(data=>{
      this.watchedMicroTopics = data;
      this.findAllTopicsByCourseId(this.courseId);
    });
  }

  findAllTopicsByCourseId(courseId: number) {    
    this.courseService._findAllTopicsByCourseId(courseId)
      .subscribe(data => {
        this.topicDetails = data.topicsData;
        this.subTopicDetails = this.topicDetails[0].subTopic;
        this.subTopicDetails = _.map(this.subTopicDetails, (st:any)=>{
          return {
            topicId: st.topicId,
            subTopicId: st.subTopicId,
            subTopicName: st.subTopicName,
            subTopicDuration: st.subTopicDuration,            
            microTopic: this.setMicroTopics(st.microTopic),
            className: this.assignSubTopicCls(st.microTopic)
          }
        });
      }, error => {

      })
  }

  setMicroTopics(mcts:any){
    let microTopics = [];
    _.map(mcts, (mt:any)=>{
      let className = '';
      if((this.watchedMicroTopics).includes(mt.microTopicId)){
        className = 'watchedMct';
      }
      microTopics.push({
        microTopicId: mt.microTopicId,
        microTopicName: mt.microTopicName,
        subTopicId: mt.subTopicId,
        microTopicDuration: mt.microTopicDuration,
        className: className,
        videoDetail: mt.videoDetail
      });
    });
    return microTopics;
  }

  assignSubTopicCls(mcts:any){
    let className = '';
    let incompleteFlag = false;
    _.each(mcts, (mt:any)=>{
      if((this.watchedMicroTopics).includes(mt.microTopicId)){
        className = 'watchedSbt';
      }else{
        incompleteFlag = true;        
      }
    });
    if(incompleteFlag){
      return '';
    }else{
      return 'watchedSbt';
    }
  }
  findAllVideosByCourseId(coursePackageId: number, courseId: number) {
    this.courseService._findAllVideosByCourseId(coursePackageId, courseId)
      .subscribe(data => {
        this.playlist = data;
      }, error => {
      }) 
  }

findAllMaterialsByCourseId(courseId: number){
this.courseService._findAllMaterialsByCourseId(courseId)
      .subscribe(data => {
        this.materialsList = data;
      }, error => {
      })
}
downloadMaterial(){
  this.courseService._downloadMaterial(this.courseId)
      .subscribe(data => {
        saveAs(data, this.courseDetails.courseName + '-lara.zip');
      });
}

  vidEnded(event: any) {
    let microTopicId = "#mct"+this.currentItem.microTopicId;  
    let microTopicIconId = "#mctIcon"+this.currentItem.microTopicId;  
    $('.mcts').removeClass("currVideo");  
    $(microTopicIconId).addClass("watchedMct");   
    $(microTopicId).addClass("watchedMct");
    if (this.isSubscribed) {
      this.currentVideoId = event.target.attributes.videoId.value;
      this.currentMicroTopicId = event.target.attributes.microTopicId.value;

      this.mctName = event.target.attributes.microTopicName.value;
      //insert one row regarding learner has watched this video on this micro topic id

      //record activity
      this.timeSpentOnVideo = new TimeSpentOnVideo(this.coursePackageId, this.currentVideoId, this.userId, this.currentMicroTopicId, 0, 0);
      this.courseService._recordVideoActivity(this.timeSpentOnVideo)
        .subscribe(data => {
          //if (data.watchedEarlier) {
            //this.currentIndex = this.currentIndex + 1;
            //let ci = _.findIndex(this.playlist, this.currentItem);
            //this.currentIndex = ci+1;
            //this.currentItem = this.playlist[this.currentIndex];

          //} else {
            this.examRecordId = data.examRecordId;
            document.getElementById("videoEndAlert").click();
            //this.currentIndex = _.findIndex(this.playlist, this.currentItem);
            //this.currentItem = this.playlist[this.currentIndex];
          //}
        },
          error => {
          });

    }else {
      this.noVideoTitle = "Package subscription required."
      this.noVideoMsg = "Please subscribe to this course package to see more videos.";
      document.getElementById("noVideoAvl").click();
    }    
  }

  attendExam() {
    this.router.navigate(['/questions'], {
      queryParams: {
        videoId: this.currentVideoId, microTopicId: this.currentMicroTopicId
      }
    });
  }
  continue() {
    this.currentIndex = this.currentIndex + 1;
    this.currentItem = this.playlist[this.currentIndex];
  }
  getCourseDetailsByCourseId(courseId: number) {
    this.courseService._getCourseDetailsByCourseId(courseId)
      .subscribe(data => {
        this.courseDetails = data;
        this.courseContentUrl = './assets/pdf/' + data.courseContent.courseContentUrl;
        this.pdfUrl= this.sanitizer.bypassSecurityTrustResourceUrl(this.courseContentUrl);
      });
  }

  getSubscriptionStatus(coursePackageId: number) {
    this.courseService._checkCoursePackageSubscription(coursePackageId).subscribe(data => {
      if (data) {
        this.isSubscribed = true;
      }
    })
  }

  showCourseContent(){
    document.getElementById("courseContentButton").click();
  }
  onRightClick()
  {
  	//return false;
	return true;
  }
}
