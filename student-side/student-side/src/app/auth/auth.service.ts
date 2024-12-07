import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { JwtResponse } from './jwt-response';
import { AuthLoginInfo } from './login-info';
import { SignUpInfo } from './signup-info';
import { ConstantsService } from '../shared/services/constants.service';
import { PwdResetRequest } from './pwd-reset-request';
import { PwdResetInfo } from './pwd-reset-info';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/signin';
  private tpoLoginUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/tpoSignIn';
  private signupUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/signup';
  private activateUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/activate';
  private signoutUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/logout';
  private otpValidateUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/validateOtp';
  private authPwdResetUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/resetPwdAuth';
  private pwdResetUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/resetPassword';
  private pwdUpdatetUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/updatePassword';
  private unsubscribeUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/unsubscribe';
  private subscribeUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/subscribe';
  private unSubscriptionMailInfoUrl = this.constantService.ENDPOINT_BASE_URL+'/auth/sendUnsubscriptionMail';

  constructor(private http: HttpClient, private constantService:ConstantsService) {
  }

  _login(credentials: AuthLoginInfo): Observable<any> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  _tpoLogin(credentials: AuthLoginInfo): Observable<any> {
    return this.http.post<JwtResponse>(this.tpoLoginUrl, credentials, httpOptions);
  }

  _signUp(info: SignUpInfo): Observable<any> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }
  _validateOtp(otpForm:Object): Observable<any> {
    return this.http.post(this.otpValidateUrl, otpForm, httpOptions);
  }

  _activateUser(activateUser: object): Observable<any>{
    return this.http.post(this.activateUrl, activateUser, httpOptions);
  }

  _subscribeUser(subscribeUser: object): Observable<any>{
    return this.http.post(this.subscribeUrl, subscribeUser, httpOptions);
  }
  _unsubscribeUser(unSubscribeUser: object): Observable<any>{
    return this.http.post(this.unsubscribeUrl, unSubscribeUser, httpOptions);
  }

  _sendUnsubscriptionMail(unSubscribeUser: Object): Observable<any>{
    return this.http.post(this.unSubscriptionMailInfoUrl, unSubscribeUser, httpOptions);
  }

  _logout(){
    return this.http.get(this.signoutUrl, httpOptions);
  }

  _authPwdResetRequest(pwdResetRequest:PwdResetRequest):Observable<any> {
    return this.http.post(this.authPwdResetUrl, pwdResetRequest, httpOptions);
  }

  _resetPassword(pwdResetInfo: PwdResetInfo): Observable<any>{
    return this.http.post(this.pwdResetUrl, pwdResetInfo, httpOptions);
  }

  _updatePassword(pwdResetInfo: PwdResetInfo): Observable<any>{
    return this.http.post(this.pwdUpdatetUrl, pwdResetInfo, httpOptions);
  }
}
