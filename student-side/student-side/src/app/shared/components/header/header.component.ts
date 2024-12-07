import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../../user/services/user.service';
import { StatusService } from '../../../shared/services/status.service';
import { LoaderService } from 'src/app/common-service/loader.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { AuthService } from 'src/app/auth/auth.service';
import { SignUpInfo } from 'src/app/auth/signup-info';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { AuthLoginInfo } from 'src/app/auth/login-info';
import { HomeService } from 'src/app/home/services/home.service';
import { PwdResetRequest } from 'src/app/auth/pwd-reset-request';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { LiveClassService } from 'src/app/admin-dash/services/live-class.service';
import { SignupDropdownService } from 'src/app/signup-dropdown.service';
declare var $: any;
import { TestinfoComponent } from 'src/app/testseries/testinfo/testinfo.component';
import { CartServiceService } from 'src/app/shopping-cart/cart-service.service';
import { CartComponent } from 'src/app/shopping-cart/cart/cart.component';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.css'],
	animations: [
		// the fade-in/fade-out animation.
		trigger('simpleFadeAnimation', [

			// the "in" style determines the "resting" state of the element when it is visible.
			state('in', style({ opacity: 1 })),

			// fade in when created. this could also be written as transition('void => *')
			transition(':enter', [
				style({ opacity: 0 }),
				animate(600)
			]),

			// fade out when destroyed. this could also be written as transition('void => *')
			transition(':leave',
				animate(600, style({ opacity: 0 })))
		])
	]
})
export class HeaderComponent implements OnInit {
	@ViewChild(CartComponent, { static: false }) cart: CartComponent

	info: any;
	uid: string;
	loaderGif: Boolean = false;
	loginForm: FormGroup;
	otpForm: FormGroup;
	signUpForm: FormGroup;
	pwdResetReqForm: FormGroup;
	enquryForm: FormGroup;
	enqurySubmitted: Boolean = false;
	enqurySuccessAlert: Boolean = false;
	isLoginSubmitted: Boolean = false;
	isRegSubmitted: Boolean = false;
	isOtpSubmitted: Boolean = false;
	isLoginValid: Boolean = false;
	isSignUpFormValid: Boolean = false;
	regSuccess: Boolean = false;
	exError: Boolean = false;
	exErrorMsg: String;
	regSuccessAlert: Boolean = false;
	loginSuccessAlert = false;
	signUpInfo: SignUpInfo;
	pwdResetRequest: PwdResetRequest;
	collegeList: Array<any> = [];
	isPwdResetReq: boolean = false;
	showLoginForm: boolean = true;
	isLoggedIn = false;
	isLoginFailed = false;
	resetPwdAuthSuccessAlert = false;
	errorMessage = '';
	role: string;
	pwdResetReqSubmitted: boolean = false;
	tpoLoginForm: FormGroup;
	isTpoLoginSubmitted: Boolean = false;
	newBatchesList: Array<any> = [];
	liveClasses = [];

	isSignupDropdownOpen = false;


	private loginInfo: AuthLoginInfo;

	constructor(private router: Router,
		private formBuilder: FormBuilder,
		private userService: UserService,
		private statusService: StatusService,
		public loaderService: LoaderService,
		private authService: AuthService,
		private tokenStorage: TokenStorageService,
		private homeService: HomeService,
		private liveClassService: LiveClassService,
		private signupDropServie: SignupDropdownService,
		private cartService: CartServiceService
	) {

	}

	ngOnInit() {
		let token = this.tokenStorage.getToken();
		if (token != null) {
			this.isLoggedIn = true;
		} else {
			this.isLoggedIn = false;
		}
		this.info = {
			token: token,
			username: this.tokenStorage.getUsername(),
			authorities: this.tokenStorage.getAuthorities()
		};
		if (this.info.authorities.length > 0) {
			this.role = this.info.authorities[0];
		}

		//this.getCollegeList();

		this.loginForm = this.formBuilder.group({
			email: ['', Validators.required],
			password: ['', Validators.required],
			role: ['ROLE_LEARNER']
		});

		this.tpoLoginForm = this.formBuilder.group({
			email: ['', Validators.required],
			password: ['', Validators.required],
			role: ['ROLE_TPO']
		});

		/*this.pwdResetReqForm = this.formBuilder.group({
			email: ['', [Validators.required, Validators.email]]
		});*/

		this.otpForm = this.formBuilder.group({
			otp: ['', Validators.required]
		});

		this.signUpForm = this.formBuilder.group({
			firstName: ['', [Validators.required, Validators.minLength(3)]],
			email: ['', [Validators.required, Validators.email]],
			//mobileNo: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
			password: ['', [Validators.required, Validators.minLength(6)]]
		});

		this.enquryForm = this.formBuilder.group({
			firstName: ['', [Validators.required, Validators.minLength(3)]],
			email: ['', [Validators.required, Validators.email]],
			mobileNo: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
			courseType: [''],
			learnerType: [''],
			specificMessage: ['']
		});


		this.getAllNewBatches();
		this.getAllLiveClasses();

		this.cartService.cartRefresh.subscribe(() => {
			this.cart.ngOnInit()
		})
	}

	getLoggedInUser() {

	}

	get enquryFormControls() {
		return this.enquryForm.controls;
	}
	get loginFormControls() {
		return this.loginForm.controls;
	}

	get tpoLoginFormControls() {
		return this.tpoLoginForm.controls;
	}

	/*get pwdResetReqFormControls(){
		return this.pwdResetReqForm.controls;
	}*/

	get otpFormControls() {
		return this.otpForm.controls;
	}

	get signUpFormControls() {
		return this.signUpForm.controls;
	}

	getFormBox() {
		this.exError = false;
	}
	login() {
		this.exError = false;
		this.isLoginSubmitted = true;
		if (this.loginForm.invalid) {
			return;
		}
		this.loaderService.show();
		this.authService._login(this.loginForm.value).subscribe(data => {
			if (data.exceptionReport != null) {
				this.exError = true;
				this.exErrorMsg = data.exceptionReport.exception.message;
			}
			else {
				sessionStorage.setItem('email', this.loginForm.get('email').value);
				this.tokenStorage.saveToken(data.accessToken);
				this.tokenStorage.saveUsername(data.username);
				this.info.username = data.username;
				this.tokenStorage.saveUserId(data.userId);
				this.tokenStorage.saveAuthorities(data.authorities);
				this.tokenStorage.setLoggedInStatus('yes')
				this.isLoginFailed = false;
				this.isLoggedIn = true;
				this.role = this.tokenStorage.getAuthorities()[0];
				this.loginSuccessAlert = true;
				this.loginCallbacks()
				setTimeout(() => {
					this.loginSuccessAlert = false;
				}, 5000);
				this.cartService.refreshCart()
			}
			this.loaderService.hide();
		}, error => {
			this.loaderService.hide();
			this.exError = true;
			this.exErrorMsg = error.error.exceptionReport.exception.message;
			console.log(this.exErrorMsg);
		});

		this.signupDropServie.isOpen$.subscribe(isOpen => {
			this.isSignupDropdownOpen = isOpen;
		});
	}

	tpoLogin() {
		this.exError = false;
		this.isTpoLoginSubmitted = true;
		if (this.tpoLoginForm.invalid) {
			return;
		}
		this.loaderService.show();
		this.authService._tpoLogin(this.tpoLoginForm.value).subscribe(data => {
			if (data.exceptionReport != null) {
				this.exError = true;
				this.exErrorMsg = data.exceptionReport.exception.message;
			}
			else {
				this.tokenStorage.saveToken(data.accessToken);
				this.tokenStorage.saveUsername(data.username);
				this.info.username = data.username;
				this.tokenStorage.saveUserId(data.userId);
				this.tokenStorage.saveAuthorities(data.authorities);
				this.isLoginFailed = false;
				this.isLoggedIn = true;
				this.role = this.tokenStorage.getAuthorities()[0];
				this.loginSuccessAlert = true;
				setTimeout(() => {
					this.loginSuccessAlert = false;
				}, 5000);
			}
			this.loaderService.hide();
		}, error => {
			this.loaderService.hide();
			this.exError = true;
			this.exErrorMsg = error.error.exceptionReport.exception.message;
		});
	}

	submitOtp() {
		this.exError = false;
		this.isOtpSubmitted = true;
		if (this.otpForm.invalid) {
		}
		let otpForm = {
			email: this.signUpForm.value.email,
			otp: this.otpForm.value.otp
		}
		this.loaderService.show();
		this.authService._validateOtp(otpForm).subscribe(data => {
			if (data.exceptionReport != null) {
				this.exError = true;
				this.exErrorMsg = data.exceptionReport.exception.message;
			} else {
				if (data.otpValidated == true) {
					this.regSuccessAlert = true;
					this.isSignUpFormValid = false;
					document.getElementById("signUpDiv").classList.remove("show");
					setTimeout(() => {
						this.regSuccessAlert = false;
					}, 5000);
				}
			}
			this.loaderService.hide();


		}, error => {
			this.exError = true;
			this.exErrorMsg = error.error.exceptionReport.exception.message;
			this.errorMessage = error.error.message;
			this.isLoginFailed = true;
			this.loaderService.hide();
		});
	}

	signUp() {
		console.log('i am in the signup');
		this.exError = false;
		this.isRegSubmitted = true;
		if (this.signUpForm.invalid) {
			return;
		}
		this.loaderService.show();
		this.signUpForm.value.role = ['learner'];
		this.signUpInfo = new SignUpInfo(this.signUpForm.value.firstName,
			this.signUpForm.value.email, this.signUpForm.value.password
		)
		this.authService._signUp(this.signUpInfo).subscribe(data => {
			console.log("signup", data);
			if (data.exceptionReport != null) {
				console.log("signup data error");
				this.exError = true;
				this.exErrorMsg = data.exceptionReport.exception.message;
				if (this.exErrorMsg.indexOf("could not execute batch") == 0) {
					this.exErrorMsg = "This email is already registered."
				}
				this.loaderService.hide();
			} else {
				this.exError = false;
				this.isSignUpFormValid = true;
				this.regSuccessAlert = true;
				this.loaderService.hide();
			}
		}, error => {
			console.log("signup error");
			this.loaderService.hide();
		});
	}

	deActivateActions() {
		this.loaderGif = false;
	}
	activateActions() {
		this.loaderGif = true;
	}

	signOut() {
		this.userService._signOut()
			.subscribe(data => {
				this.getLoggedInUser();
			}, error => {
			});
	}

	logout() {
		this.authService._logout().subscribe(data => {
			this.tokenStorage.signOut();
			this.logoutCallbacks()
			this.reloadPage();
		});
	}

	reloadPage() {
		window.location.href = '/';
	}

	getCollegeList() {
		this.homeService._getCollegeList().subscribe(data => {
			this.collegeList = data.collegeList;
		});
	}

	/*getPwdReqForm(){
		this.showLoginForm = false;
		this.isPwdResetReq = true;		
	}*/
	/*pwdResetReqSubmit(){		
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
				this.isPwdResetReq = false;
				setTimeout(() => {						
					this.regSuccessAlert = false;
				}, 5000);				
				this.isPwdResetReq = false;
			}
			this.loaderService.hide();
		}, error=>{
		});
	}*/




	getAllNewBatches() {
		this.homeService._getAllNewBatches().subscribe(data => {
			this.newBatchesList = data;
		}, error => {
			console.log(error);
		});
	}

	getAllLiveClasses() {
		this.liveClassService._readAllLiveClassesForHomePage().subscribe(
			results => {
				this.liveClasses = results;
				console.log('live classes', this.liveClasses.length)
			},
			error => {
				console.log('error in the live clasees', error);
			}
		)
	}
	getDate(date: string) {
		return date.substr(0, date.indexOf('T'));
	}

	getTime(date: string) {
		return date.substr(date.indexOf('T') + 1).substr(0, 5);
	}
	enqury() {
		this.exError = false;
		this.enqurySubmitted = true;
		if (this.enquryForm.invalid) {
			return;
		}
		this.loaderService.show();
		this.homeService.__enqury(this.enquryForm).subscribe(data => {
			console.log(data);
			this.enqurySuccessAlert = true;
			//this.enquryForm.reset();
			this.loaderService.hide();
		}, error => {
			console.log(error);
			this.loaderService.hide();
		});
	}

	loginCallbacks() {
		this.cart.ngOnInit()
	}

	logoutCallbacks() {
		this.cart.ngOnInit()
	}



}





