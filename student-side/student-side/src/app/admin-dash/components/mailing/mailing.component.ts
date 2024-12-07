import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AdminService } from '../../services/admin.service';
declare var $:any;
import * as _ from 'underscore';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { LoaderService } from 'src/app/common-service/loader.service';

@Component({
  selector: 'app-mailing',
  templateUrl: './mailing.component.html',
  styleUrls: ['./mailing.component.css'],
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
export class MailingComponent implements OnInit {
  learnersList:any;
  subLearnersList:any;
  unSubLearnersList:any;
  checkedUserEmails:any = [];
  sendBulkEmailForm: FormGroup;
  successAlert = false;
  successMsg:string;
  mappedActCodeUserEmails:any = [];
  constructor(private router:Router, private activatedRoute: ActivatedRoute, private adminService:AdminService,
    private fb:FormBuilder, private loaderService:LoaderService) { }

  ngOnInit() {
    this.sendBulkEmailForm = this.fb.group({
      mailSubject: ['', Validators.required],
      mailContent: ['', Validators.required],
      userEmailsList: this.fb.array([])      
    });    

    this.getAllLearners();
  }

  getAllLearners(){
    this.adminService._getAllLearners().subscribe(data=>{
      this.learnersList = data;
      this.subLearnersList = this.learnersList.filter((lt:any)=>{
        return (lt.mailSubscription == 1)
      });
      this.mappedActCodeUserEmails = _.map(this.subLearnersList, ((sb: any) => {
        return {
          email: sb.email+":"+sb.activationCode
        }
      }));
      this.unSubLearnersList = this.learnersList.filter((lt:any)=>{
        return (lt.mailSubscription == 0)
      });      
    });
  }
  openTab(evt:any, tabName:string) {
    // Declare all variables
    var i, hoztabcnt, tablinks;
  
    // Get all elements with class="tabcontent" and hide them
    hoztabcnt = document.getElementsByClassName("hoztabcnt");
    for (i = 0; i < hoztabcnt.length; i++) {
      hoztabcnt[i].style.display = "none";
    }
  
    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
  
    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
  }

  chkUnchkAllEmails(event:any){
    if(event.target.checked){                
      $(".emailCheckBox").prop( "checked", true );      
      _.each(this.mappedActCodeUserEmails, (me:any)=>{
        this.checkedUserEmails.push(me.email);
      });
    }else{
      $(".emailCheckBox").prop( "checked", false );
      this.checkedUserEmails = [];
    }
    console.log(this.checkedUserEmails);
  }

  pushUserEmail(event:any){
    let emailId = event.target.getAttribute('id');
    let activeIndex = this.checkedUserEmails.indexOf(emailId);
    if(event.target.checked){  
      if(activeIndex > -1){
        this.checkedUserEmails.splice(activeIndex, 1); 
      }else{
        this.checkedUserEmails.push(emailId);
      }      
    }else{
      if(activeIndex > -1){
        this.checkedUserEmails.splice(activeIndex, 1); 
      }
    }
    console.log(this.checkedUserEmails);
  }

  sendBulkEmail(){
    this.sendBulkEmailForm.value.userEmailsList = this.checkedUserEmails;    
    this.loaderService.show();
    this.adminService._sendBulkEmail(this.sendBulkEmailForm.value).subscribe(data=>{
      
      if(data.exceptionReport == null){
        this.loaderService.hide();
        this.successAlert = true;
        this.successMsg = 'Mail sent successfully';
      }
    })
  }

}
