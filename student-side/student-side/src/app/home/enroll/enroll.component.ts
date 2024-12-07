import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HomeService } from '../services/home.service';
import { AuthService } from 'src/app/auth/auth.service';
import { SignUpInfo } from 'src/app/auth/signup-info';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './enroll.component.html',
  styleUrls: ['./enroll.component.css']
})
export class EnrollComponent implements OnInit {

  signupForm: FormGroup;
  formSubmitted = false;
  signUpInfo: SignUpInfo;
  duplicateEmail;
  courseId;
  constructor(private service: AuthService, 
              private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute ) {

               }

  ngOnInit() {
    this.activatedRoute.queryParams
    .subscribe(params => {
     this.courseId = params.id;
      //console.log(this.courseId); 
    }
  );


    this.signupForm = this.formBuilder.group({
      firstName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }
  get firstName(){
    return this.signupForm.get('firstName');
  }
  get email(){
    return this.signupForm.get('email');
  }
  get password(){
    return this.signupForm.get('password');
  }
  save(){
    if(this.signupForm.invalid){
      this.signupForm.markAllAsTouched();
      return;
    }
		this.signUpInfo = new SignUpInfo(this.signupForm.value.firstName, 
      this.signupForm.value.email, this.signupForm.value.password)
      this.service._signUp(this.signUpInfo).subscribe(data => {
        console.log("signup", data);
        if(data.exceptionReport != null){
            this.duplicateEmail = "This email is already registered. You can just activate from your mailbox. Search in the SPAM folder also"
        }
        this.formSubmitted = true;
    });


  }

}
