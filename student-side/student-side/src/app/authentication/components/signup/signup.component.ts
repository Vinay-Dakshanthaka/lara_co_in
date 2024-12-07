import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user/services/user.service';
import { StatusService } from 'src/app/shared/services/status.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  
	signUpForm: FormGroup;
	isLoginSubmitted: Boolean = false;
	isRegSubmitted: Boolean = false;
	isOtpSubmitted: Boolean = false;
	isLoginValid: Boolean = false;
	isLoggedIn: Boolean = false;
	firstName: String;
	regSuccess: Boolean = false;
	exError: Boolean = false;
	exErrorMsg: String
	constructor(private router: Router, private formBuilder: FormBuilder, 
		private userService: UserService, 
		private statusService: StatusService
		) { 

	}

	ngOnInit() {
    
		this.signUpForm = this.formBuilder.group({
			firstName: ['', Validators.required],
			email: ['', Validators.required],
			mobileNo: ['', Validators.required],
			password: ['', Validators.required]
		});		
	}
	
	get signUpFormControls() {
		return this.signUpForm.controls;
	}

	signUp() {	
		if (this.signUpForm.invalid) {
			return;
		}
		this.userService._signUp(this.signUpForm.value).subscribe(data => {
			this.isRegSubmitted = true;
		}, error => {
		})
	}

}
