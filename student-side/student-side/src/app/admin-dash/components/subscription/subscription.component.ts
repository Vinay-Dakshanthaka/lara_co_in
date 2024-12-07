import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AdminService } from '../../services/admin.service';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: []
})
export class SubscriptionComponent implements OnInit {
  learnersList:any;
  subLearnersList:any;
  unSubLearnersList:any;
  learnerSearchForm: FormGroup;
  srchErrorFlg = false;
  srchErrFrmMsg:string;
  constructor(private router:Router, private activatedRoute:ActivatedRoute, private adminService:AdminService, private formBuilder:FormBuilder) { }

  ngOnInit() {

    this.learnerSearchForm = this.formBuilder.group({
      userId: [''],
      firstName: ['' ],
      email: [''],
      mobileNo: []    
    });

    this.getAllLearners();
  }

  get learnerSearchFormControls() {
		return this.learnerSearchForm.controls;
  }
  
  getAllLearners(){
    this.adminService._getAllLearners().subscribe(data=>{
      this.learnersList = data;
      this.subLearnersList = this.learnersList.filter((lt:any)=>{
        return (lt.userId !=null)
      });
      this.unSubLearnersList = this.learnersList.filter((lt:any)=>{
        return (lt.userId ==null)
      });      
    });
  }

  openTab(evt, tabName) {
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

  searchLearnerData(){
    document.getElementById("defaultShow").click();
    this.srchErrorFlg = false;
    let userId = this.learnerSearchForm.value.userId;
    let firstName = this.learnerSearchForm.value.firstName;
    let mobileNo = this.learnerSearchForm.value.mobileNo;
    let email = this.learnerSearchForm.value.email;
    if(userId == '' && firstName == '' && mobileNo == null && email == '' ){
      this.srchErrorFlg = true;
      this.srchErrFrmMsg = 'Please eneter any parameter';
      return;
    }
    this.adminService._getAllLearners().subscribe(data=>{
      this.learnersList = data;
      this.learnersList = this.learnersList.filter((lt:any)=>{
        if(userId != null && userId != ''){
          return (lt.userId === userId);
        }else if(firstName != null && firstName != ''){
          return (lt.firstName === firstName);
        }else if(mobileNo != null && mobileNo != ''){
          return (lt.mobileNo === mobileNo);
        }else if(email != null && email != ''){
          return (lt.email === email);
        }      
      });       
    });
  }

  clearLearnerSearchForm(){
    this.srchErrorFlg = false;
    this.learnerSearchForm = this.formBuilder.group({
      userId: [''],
      firstName: ['' ],
      email: [''],
      mobileNo: []    
    });
    this.getAllLearners();
  }
}
