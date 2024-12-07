import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { LoaderService } from 'src/app/common-service/loader.service';
import { AuthService } from 'src/app/auth/auth.service';
import { PwdResetInfo } from 'src/app/auth/pwd-reset-info';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: []
})
export class ChangePasswordComponent implements OnInit {

  chPwdForm: FormGroup;
  userId: string;
  activationCode: string;
  pwdResetInfo: PwdResetInfo;
  exError: boolean = false;
  exErrorMsg: string;
  pwdChanged: boolean = false;
  ischPwdFormSubmitted: boolean = false;


  constructor(private router: Router, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private loaderService: LoaderService,
    private authService: AuthService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params=>{
      this.userId = params['userId'];
      this.activationCode = params['actCode'];
    });

    this.chPwdForm = this.formBuilder.group({			
      userId: [],			
      activationCode: [],
      password: ['', [Validators.required, Validators.minLength(6)]],
      cPassword: ['', Validators.required]
		});	
  }

  get chPwdFormControls() {
		return this.chPwdForm.controls;
  }
  
  resetPassword(){    
    this.ischPwdFormSubmitted = true;
		if (this.chPwdForm.invalid) {
			return;
    }
    if(this.chPwdForm.value.password != this.chPwdForm.value.cPassword){
      this.exError = true;
      this.exErrorMsg = "Confirm password should be same as password";
      return;
    }else{
      this.exError = false;
      this.exErrorMsg = '';
    }
    this.loaderService.show();
    
    this.pwdResetInfo = new PwdResetInfo('', this.userId, 
    this.chPwdForm.value.password, 
    this.chPwdForm.value.cPassword, 
    this.activationCode, '', '');
    this.authService._resetPassword(this.pwdResetInfo).subscribe(data=>{
      if(data.exceptionReport != null){
        this.exError = true;
				this.exErrorMsg = data.exceptionReport.exception.message;
      }else{
        this.pwdChanged = true;
        setTimeout(() => {
					document.getElementById("redirectBtn").click();					
				  }, 2000);
      }    
      this.loaderService.hide();  
    });
  }

  redirectLink(){
    this.router.navigate(['']);
  }
}
