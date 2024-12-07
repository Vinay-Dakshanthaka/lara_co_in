import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserProfileService } from '../../services/user-profile.service';
import { EducationService } from '../../services/education.service';
import * as _ from 'underscore';
import { SkillService } from '../../services/skill.service';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { TpoService } from 'src/app/tpo-dash/services/tpo.service';
declare var $: any;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  animations: [
    trigger('simpleFadeAnimation', [
      state('in', style({ opacity: 1 })),
      transition(':enter', [style({ opacity: 0 }), animate(600)]),
      transition(':leave', animate(600, style({ opacity: 0 })))
    ])
  ]
})
export class ProfileComponent implements OnInit {
  profileDetailForm: FormGroup;
  userId: string;
  firstName: string = '';
  lastName: string = '';
  educationForm: FormGroup;
  learnerSkillForm: FormGroup;
  learnerWorkExpForm: FormGroup;
  educationList: [];
  eduSpecList: [];
  skillsList: [];
  skillLevelList: [];
  updateRespAlert = false;
  updateRespAlertText: string = '';
  isEduFormSubmitted: boolean = false;
  islrSkillFormSubmitted: boolean = false;
  isLrWkExpFormSubmitted: boolean = false;
  coursePackagesList: Array<any>;
  constructor(private fb: FormBuilder, private router: Router, private activatedRoute: ActivatedRoute,
    private userProfileService: UserProfileService, private educationService: EducationService,
    private skillService: SkillService, private tpoService: TpoService) { }

  ngOnInit() {
    this.userId = window.sessionStorage.getItem('UserId');
    $(document).ready(function () {
      $('#datepicker').datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        yearRange: "1960:+nn",
        maxDate: new Date('2010-3-26'),
      });
      $('.datepicker').datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        yearRange: "1960:+nn",
        maxDate: new Date('2010-3-26'),
      });
    });

    //find all lists data
    this.getEducationList();
    this.getEduSpecList();
    this.getSkillsList();
    this.getSkillLevelList();

    this.getUserProfileData(this.userId);
    this.getAllCoursePackForLearner();
    //profileDetailForm
    this.profileDetailForm = this.fb.group({
      userId: [this.userId],
      firstName: ['', Validators.required],
      email: ['', Validators.required],
      mobileNo: ['', Validators.required],
      lastName: ['', Validators.required],
      gender: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      learnerAddress: this.fb.array([this.fb.group({ id: '', countryId: '', stateId: '', city: '', houseNo: '', street: '', zipcode: '', addressType: 'Current' }),
      this.fb.group({ id: '', countryId: '', stateId: '', city: '', houseNo: '', street: '', zipcode: '', addressType: 'Permanent' })
      ])
    });
    //educationForm
    this.educationForm = this.fb.group({
      educationFormRows: this.fb.array([this.fb.group({
        id: '',
        educationId: ['', Validators.required],
        eduSpecId: ['', Validators.required],
        percentage: ['', [Validators.required]],
        yearOfPassOut: ['', Validators.required],
        boardName: ['', Validators.required],
        filterdEdArray: []
      })])
    });
    //learnerSkillForm
    this.learnerSkillForm = this.fb.group({
      learnerSkillFormRows: this.fb.array([this.fb.group({
        id: '',
        userId: this.userId,
        skillId: ['', Validators.required],
        skillLevelId: ['', Validators.required]
      })])
    });
    //learnerWorkExpForm
    this.learnerWorkExpForm = this.fb.group({
      learnerWorkExpFormRows: this.fb.array([this.fb.group({
        workSessionId: '',
        userId: this.userId,
        companyName: ['', Validators.required],
        dateOfJoining: '',
        dateOfExit: '',
        joiningSalary: ['', Validators.required],
        exitSalary: ['', Validators.required]
      })
      ])
    });
  } //ng onInit ends here

  getEducationList() { this.educationService._getEducationList().subscribe(data => { this.educationList = data.educationResp }, error => { }); }
  getEduSpecList() { this.educationService._getEduSpecList().subscribe(data => { this.eduSpecList = data.eduSpecList }); }
  getEduSpecListByEduId(eduId: number, index: number) {
    this.educationForm.value.educationFormRows[index].filterdEdArray = this.eduSpecList.filter((eduSpec: any) => {
      return eduSpec.educationId == eduId;
    });
  }

  getEduSpecListFiltered(eduId, index) {
    this.educationForm.value.educationFormRows[index].filterdEdArray = this.eduSpecList.filter((eduSpec: any) => {
      return eduSpec.educationId == eduId;
    });
  }

  getSkillsList() { this.skillService._getSkillsList().subscribe(data => { this.skillsList = data.skillsData }) }
  getSkillLevelList() { this.skillService._getSkillLevelList().subscribe(data => { this.skillLevelList = data.skillLevelData }) }

  get learnerAddress() { return this.profileDetailForm.get('learnerAddress') as FormArray; }
  get profileDetailFormControls() { return this.profileDetailForm.controls; }

  getUserProfileData(uid: string) {
    this.userProfileService._getUserProfileData(this.userId)
      .subscribe(data => {
        if (data != null) {
          let lrAddressArr = [];
          let lrEducationArr = [];
          let lrSkillArr = [];
          let lrWorkExpArr = [];
          _.each(data[0].learnerAddress, (la) => { lrAddressArr.push(this.fb.group(la)); });
          _.each(data[0].learnerEducation, (le: any) => {
            le.filterdEdArray = [];
            var filteredEduSpec = this.eduSpecList.filter((eduSpec: any) => {
              return eduSpec.educationId == le.educationId;
            });
            le.filterdEdArray.push(filteredEduSpec);
            lrEducationArr.push(this.fb.group(le));
          });
          _.each(data[0].learnerSkill, (ls) => { lrSkillArr.push(this.fb.group(ls)); });
          _.each(data[0].learnerWorkExp, (lwe) => { lrWorkExpArr.push(this.fb.group(lwe)); });
          let dob = '';
          if (data[0].dateOfBirth != null) {
            dob = data[0].dateOfBirth.split("T")[0];
          }
          this.firstName = data[0].firstName;
          this.lastName = data[0].lastName;
          this.profileDetailForm = this.fb.group({
            userId: data[0].userId,
            firstName: data[0].firstName,
            email: data[0].email,
            mobileNo: data[0].mobileNo,
            lastName: data[0].lastName,
            gender: data[0].gender,
            dateOfBirth: dob,
            //learnerAddress: this.fb.array([this.fb.group({id:lr[0].id,userId:this.uid, countryId:lr[0].countryId, stateId:lr[0].stateId, city:lr[0].city,houseNo:lr[0].houseNo,street:lr[0].stateId,zipcode:lr[0].zipcode, addressType:lr[0].addressType}), this.fb.group({id:lr[1].id, userId:this.uid, countryId:lr[1].countryId, stateId:lr[1].stateId, city:lr[1].city,houseNo:lr[1].houseNo,street:lr[1].street,zipcode:lr[1].zipcode, addressType:lr[1].addressType})])
            learnerAddress: this.fb.array(lrAddressArr)
          });
          if (lrEducationArr.length > 0) {
            this.educationForm = this.fb.group({

              educationFormRows: this.fb.array(lrEducationArr)
            });
          }
          if (lrSkillArr.length > 0) {
            this.learnerSkillForm = this.fb.group({ learnerSkillFormRows: this.fb.array(lrSkillArr) });
          }
          if (lrWorkExpArr.length > 0) {
            this.learnerWorkExpForm = this.fb.group({ learnerWorkExpFormRows: this.fb.array(lrWorkExpArr) });
          }
        }
      });
  }

  get educationFormRows() { return this.educationForm.get('educationFormRows') as FormArray; }
  addEducationFormRow() {
    this.isEduFormSubmitted = false;
    this.educationFormRows.push(this.fb.group({
      id: '',
      educationId: ['', Validators.required],
      eduSpecId: ['', Validators.required],
      percentage: ['', Validators.required],
      yearOfPassOut: ['', Validators.required],
      boardName: ['', Validators.required],
      filterdEdArray: []
    }));
  }
  deleteEducationFormRow(index: any) {
    this.isEduFormSubmitted = false;
    let eduId = this.educationFormRows.controls[index].value.id;
    this.educationFormRows.removeAt(index);
    this.userProfileService._deleteEducationFormRow(eduId).subscribe(data => {
      this.updateRespAlert = true;
      this.updateRespAlertText = 'Education records has been deleted successfully.';
      this.scrollToTop();
      setTimeout(() => {
        this.updateRespAlert = false;
      }, 3000);
    });
  }

  //learnerSkillForm
  get learnerSkillFormRows() { return this.learnerSkillForm.get('learnerSkillFormRows') as FormArray; }
  addLearnerSkillFormRow() {
    this.islrSkillFormSubmitted = false;
    this.learnerSkillFormRows.push(this.fb.group({
      id: '', userId: this.userId,
      skillId: ['', Validators.required],
      skillLevelId: ['', Validators.required]
    }));
  }

  deleteLearnerSkillFormRow(index: any) {
    this.islrSkillFormSubmitted = false;
    let skillId = this.learnerSkillFormRows.controls[index].value.id;
    this.learnerSkillFormRows.removeAt(index);
    this.userProfileService._deleteSkillFormRow(skillId).subscribe(data => {
      this.updateRespAlert = true;
      this.updateRespAlertText = 'Skill record has been deleted successfully.';
      this.scrollToTop();
      setTimeout(() => {
        this.updateRespAlert = false;
      }, 3000);
    });
  }

  //learnerWorkExpForm
  get learnerWorkExpFormRows() { return this.learnerWorkExpForm.get('learnerWorkExpFormRows') as FormArray; }
  addLearnerWorkExpFormRow() {
    this.isLrWkExpFormSubmitted = false;
    this.learnerWorkExpFormRows.push(this.fb.group({
      workSessionId: '',
      userId: this.userId,
      companyName: ['', Validators.required],
      dateOfJoining: '',
      dateOfExit: '',
      joiningSalary: ['', Validators.required],
      exitSalary: ['', Validators.required]
    })
    );
  }
  deleteLearnerExpFormRow(index: any) {
    this.isLrWkExpFormSubmitted = false;
    let lWexId = this.learnerWorkExpFormRows.controls[index].value.workSessionId;
    this.learnerWorkExpFormRows.removeAt(index);
    this.userProfileService._deleteLearnerExpFormRow(lWexId).subscribe(data => {
      this.updateRespAlert = true;
      this.updateRespAlertText = 'Work experience record has been deleted successfully.';
      this.scrollToTop();
      setTimeout(() => {
        this.updateRespAlert = false;
      }, 3000);
    });
  }


  updateProfile() {
    let dob = $('input#datepicker').val();
    this.profileDetailForm.value.dateOfBirth = dob;
    this.userProfileService._updateProfile(this.profileDetailForm.value.userId, this.profileDetailForm.value)
      .subscribe(data => {
        this.updateRespAlert = true;
        this.updateRespAlertText = 'Your profile has been updated successfully.';
        this.scrollToTop();
        setTimeout(() => {
          this.updateRespAlert = false;
        }, 5000);
      });
  }

  updateLearnerEdu() {
    this.isEduFormSubmitted = true;
    if (this.educationForm.invalid) {
      return;
    }
    this.educationService._updateLearnerEdu(this.educationForm.value.educationFormRows).subscribe(data => {
      this.isEduFormSubmitted = false;
      if (data.exceptionReport == null) {
        this.updateRespAlert = true;
        this.updateRespAlertText = 'Education records has been updated successfully.';
        this.scrollToTop();
        setTimeout(() => {
          this.updateRespAlert = false;
        }, 5000);
      }
    });
  }

  updateLearnerSkills() {
    this.islrSkillFormSubmitted = true;
    if (this.learnerSkillForm.invalid) {
      return;
    }
    this.skillService._updateLearnerSkills(this.learnerSkillForm.value.learnerSkillFormRows)
      .subscribe(data => {
        this.islrSkillFormSubmitted = false;
        if (data.exceptionReport == null) {
          this.updateRespAlert = true;
          this.updateRespAlertText = 'Skill records has been updated successfully.';
          this.scrollToTop();
          setTimeout(() => {
            this.updateRespAlert = false;
          }, 5000);
        }
      });
  }

  updateLearnerWorkExp() {
    this.isLrWkExpFormSubmitted = true;
    if (this.learnerWorkExpForm.invalid) {
      return;
    }
    this.skillService._updateLearnerWorkExp(this.learnerWorkExpForm.value.learnerWorkExpFormRows)
      .subscribe(data => {
        if (data.exceptionReport == null) {
          this.updateRespAlert = true;
          this.updateRespAlertText = 'Work experience records has been updated successfully.';
          this.scrollToTop();
          setTimeout(() => {
            this.updateRespAlert = false;
          }, 5000);
        }
      })
  }
  scrollToTop() {
    $('body,html').animate({
      scrollTop: 200
    }, 800);
  }

  openCity(evt, cityName) {
    $('#datepicker').datepicker({
      dateFormat: "yy-mm-dd",
      changeMonth: true,
      changeYear: true,
      yearRange: "1960:+nn",
      maxDate: new Date('2010-3-26'),
    });
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
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
  }

  get learnerAddressFormRows() { return this.profileDetailForm.get('learnerAddress') as FormArray; }
  copyAddress(event: any) {
    let lrAddressValue = this.profileDetailForm.get('learnerAddress');
    if (event.target.checked) {
      lrAddressValue.value[1].id = lrAddressValue.value[0].id;
      lrAddressValue.value[1].houseNo = lrAddressValue.value[0].houseNo;
      lrAddressValue.value[1].userId = lrAddressValue.value[0].userId;
      lrAddressValue.value[1].street = lrAddressValue.value[0].street;
      lrAddressValue.value[1].countryId = lrAddressValue.value[0].countryId;
      lrAddressValue.value[1].stateId = lrAddressValue.value[0].stateId;
      lrAddressValue.value[1].city = lrAddressValue.value[0].city;
      lrAddressValue.value[1].zipcode = lrAddressValue.value[0].zipcode;
      lrAddressValue.value[1].addressType = "Permanent";
    } else {
      lrAddressValue.value[1].id = '';
      lrAddressValue.value[1].houseNo = '';
      lrAddressValue.value[1].userId = lrAddressValue.value[0].userId;
      lrAddressValue.value[1].street = '';
      lrAddressValue.value[1].countryId = '';
      lrAddressValue.value[1].stateId = '';
      lrAddressValue.value[1].city = '';
      lrAddressValue.value[1].zipcode = '';
      lrAddressValue.value[1].addressType = "Permanent";
    }
    this.profileDetailForm.get('learnerAddress').patchValue(lrAddressValue.value);
  }

  uncheckAddressCheckBox(event: any) {
    $("#addressChkBox").prop("checked", false);
  }


  getAllCoursePackForLearner() {
    this.userProfileService._getAllCoursePackForLearner()
      .subscribe(data => {
        console.log(data);

      });
  }
}
