import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PwdResetInfo } from 'src/app/auth/pwd-reset-info';
import { Router, ActivatedRoute } from '@angular/router';
import { LoaderService } from 'src/app/common-service/loader.service';
import { AuthService } from 'src/app/auth/auth.service';
import { trigger, state, style, transition, animate } from '@angular/animations';
@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: []
})
export class UpdatePasswordComponent implements OnInit {

  updatePwdForm: FormGroup;
  exError: boolean = false;
  exErrorMsg: string;
  pwdChanged: boolean = false;
  pwdResetInfo: PwdResetInfo;
  isupdatePwdFormSubmitted: boolean = false;


  constructor(private router: Router, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private loaderService: LoaderService,
    private authService: AuthService) { }

  ngOnInit() {
    /*this.activatedRoute.params.subscribe(params=>{

    });*/

    this.updatePwdForm = this.formBuilder.group({			
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      cPassword: ['', Validators.required]
		});	
  }

  get updatePwdFormControls() {
		return this.updatePwdForm.controls;
  }
  
  updatePassword(){    
    this.isupdatePwdFormSubmitted = true;
		if (this.updatePwdForm.invalid) {
			return;
    }
    if(this.updatePwdForm.value.newPassword != this.updatePwdForm.value.cPassword){
      this.exError = true;
      this.exErrorMsg = "Confirm password should be same as new password";
      return;
    }else{
      this.exError = false;
      this.exErrorMsg = '';
    }
    this.loaderService.show();
    
    this.pwdResetInfo = new PwdResetInfo('', '', 
    '', 
    this.updatePwdForm.value.cPassword, 
    '', this.updatePwdForm.value.currentPassword, this.updatePwdForm.value.newPassword);
    this.authService._updatePassword(this.pwdResetInfo).subscribe(data=>{
      if(data.exceptionReport != null){
        this.exError = true;
				this.exErrorMsg = data.exceptionReport.exception.message;
      }else{
        this.pwdChanged = true;               
      }    
      this.loaderService.hide();  
    });
  }
}
