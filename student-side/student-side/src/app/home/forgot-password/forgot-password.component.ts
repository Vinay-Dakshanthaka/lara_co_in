import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { LoaderService } from 'src/app/services/loader.service';
import { AuthService } from 'src/app/auth/auth.service';
import { PwdResetRequest } from 'src/app/auth/pwd-reset-request';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: []
})
export class ForgotPasswordComponent implements OnInit {
  pwdResetReqForm: FormGroup;
  pwdResetReqSubmitted: boolean = false;
  pwdResetRequest: PwdResetRequest;
  exError:boolean = false;
  exErrorMsg:boolean = false;
  resetPwdAuthSuccessAlert:boolean = false;

  constructor(private formBuilder: FormBuilder,
    public loaderService: LoaderService,
    private authService: AuthService) { }

  ngOnInit() {
    this.pwdResetReqForm = this.formBuilder.group({
			email: ['', [Validators.required, Validators.email]]
		});
  }

  get pwdResetReqFormControls(){
		return this.pwdResetReqForm.controls;
  }
  
  pwdResetReqSubmit(){		
		this.pwdResetReqSubmitted = true;
		if (this.pwdResetReqForm.invalid) {
			return;
		}
		this.loaderService.show();
		this.pwdResetRequest = new PwdResetRequest(this.pwdResetReqForm.value.email);
		this.authService._authPwdResetRequest(this.pwdResetRequest).subscribe(data=>{
			if(data.exceptionReport != null){
				this.exError = true;
				this.exErrorMsg = data.exceptionReport.exception.message;
			}else{				
				this.resetPwdAuthSuccessAlert = true;
				this.exError = false;			
			}
			this.loaderService.hide();
		}, error=>{
		});
	}

}
