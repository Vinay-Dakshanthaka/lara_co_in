import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/app/course/services/course.service';
import * as _ from 'underscore';
declare var $:any;
import { AdminService } from '../../services/admin.service';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-edit-subscription',
  templateUrl: './edit-subscription.component.html',
  styleUrls: [],
  animations: [
		// the fade-in/fade-out animation.
		trigger('simpleFadeAnimation', [
	
		  // the "in" style determines the "resting" state of the element when it is visible.
		  state('in', style({opacity: 1})),
	
		  // fade in when created. this could also be written as transition('void => *')
		  transition(':enter', [
			style({opacity: 0}),
			animate(600 )
		  ]),
	
		  // fade out when destroyed. this could also be written as transition('void => *')
		  transition(':leave',
			animate(600, style({opacity: 0})))
		])
	  ]
})
export class EditSubscriptionComponent implements OnInit {
  mngSubscriptionForm: FormGroup;
  successAlert = false;
  successMsg = '';
  email: string;
  userId: string;
  mobileNo: string;
  coursePackageList: any;
  activateCoursePkgList:any = [];
  deActivateCoursePkgList:any = [];
  mappedCoursePackageList: any;
  status = 'Active';
  subscription = 'Subscribed';
  subscribedCpgList: any;
  constructor(private router: Router, private activatedRoute: ActivatedRoute, private courseService: CourseService,
    private fb: FormBuilder, private adminService:AdminService) { }

  ngOnInit() {

    this.activatedRoute.params.subscribe(params => {
      this.email = params['email'];
    });

    this.getLearnerDetail(this.email);
    this.mngSubscriptionForm = this.fb.group({
      userId: ['', Validators.required],
      actCoursePkgList: this.fb.array([]),
      deActCoursePkgList: this.fb.array([])
    });    
  }

  getLearnerDetail(email:string){
    this.adminService._getLearnerDetail(email).subscribe(data=>{
      this.userId = data.userId;
      this.mobileNo = data.mobileNo;
      this.getCoursePackageList();
    });
  }
  manageSubscription(){
    let actCoursePkgList = this.activateCoursePkgList;
    let deActCoursePkgList = this.deActivateCoursePkgList;
    this.mngSubscriptionForm.value.userId = this.userId;
    this.mngSubscriptionForm.value.actCoursePkgList = actCoursePkgList;
    this.mngSubscriptionForm.value.deActCoursePkgList = deActCoursePkgList;
    this.adminService._manageSubscription(this.mngSubscriptionForm.value).subscribe(data=>{
      if(data.exceptionReport == null){
        this.successAlert = true;
        this.successMsg = 'Learner subscription has been updated successfully';
      }
    })
  }

  /*chkUnchkCpg(event:any){
    let cpgId = parseInt(event.target.getAttribute('id').split('-')[1]);
      if(event.target.checked){         
        let index = this.activateCoursePkgList.indexOf(cpgId);
        if(index > -1){
          this.activateCoursePkgList.splice(index, 1); 
        }else{
          this.activateCoursePkgList.push(cpgId);
        }
      }else{
        let index = this.activateCoursePkgList.indexOf(cpgId);
        if (index > -1) {
          this.deActivateCoursePkgList.splice(index, 1);          
        }else{
          this.deActivateCoursePkgList.push(cpgId);
        }
        
      } 
  }*/

  chkUnchkCpg(event:any){
    let cpgId = parseInt(event.target.getAttribute('id').split('-')[1]);
    let activeIndex = this.activateCoursePkgList.indexOf(cpgId);
    let deActiveIndex = this.deActivateCoursePkgList.indexOf(cpgId);
      if(event.target.checked){                
        if(activeIndex > -1){
          this.activateCoursePkgList.splice(activeIndex, 1); 
        }else{
          this.activateCoursePkgList.push(cpgId);
        }
        if(deActiveIndex > -1){
          this.deActivateCoursePkgList.splice(deActiveIndex, 1);
        }
      }else{        
        if (activeIndex > -1) {
          this.activateCoursePkgList.splice(activeIndex, 1);                  
        }
        if(deActiveIndex > -1){
          this.deActivateCoursePkgList.splice(deActiveIndex,1)
        }else{
          this.deActivateCoursePkgList.push(cpgId);
        }
        
      } 
  }

  getCoursePackageList() {
    this.courseService._getCoursePackageList().subscribe(data => {
      this.coursePackageList = data.coursePackageList;
      this.getSubscribedPackages(this.userId);
    })
  }

  getSubscribedPackages(userId:string){
    this.adminService._getSubscribedPackages(userId).subscribe(data=>{
      this.subscribedCpgList = data;
      this.activateCoursePkgList = data;
      _.each(this.subscribedCpgList, (sbPkg:any)=>{
        let coursePackageIdAttr = '#cpg-'+ sbPkg;
        $(coursePackageIdAttr).prop( "checked", true );
      });
    });
  }
  /*getUserSubscriptionDetail(userId: string) {
    this.courseService._getUserSubscriptionDetail(userId).subscribe(data => {
      this.subscribedCpgList = data;
      if (this.subscribedCpgList.length > 0) {
        this.userId = this.subscribedCpgList[0].userId;
        this.email = this.subscribedCpgList[0].email;
        this.mobileNo = this.subscribedCpgList[0].mobileNo;
        if (this.subscribedCpgList[0].status == null) {
          this.subscription = 'UnSubscribed';
          this.status = 'Inactive';
        } else if (this.subscribedCpgList[0].status == 0) {
          this.status = 'Inactive';
        } else if (this.subscribedCpgList[0].status == 1) {
          this.status = 'Active';
        }
       
      }
      this.mappedCoursePackageList = _.map(this.coursePackageList, ((cp: any, key) => {
        return {
          coursePackageId: cp.coursePackageId,
          coursePackageName: cp.coursePackageName,
          status: this.status,
          subscription: this.subscription
        }
      }));
      this.getSubscribedPackages(userId);
    });
  }*/

}
