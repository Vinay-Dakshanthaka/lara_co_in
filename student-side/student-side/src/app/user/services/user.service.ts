import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
	providedIn: 'root'
})
export class UserService {

	constructor(private http: HttpClient, private constantService: ConstantsService) { }

	_signOut(): Observable<any> {
		return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/signout`);
	}

	/*_login(learner: Object): Observable<any> {
		return this.http.post(`${this.userBaseUrl}` + `/login`, learner);
	}*/

	_signUp(learner: Object): Observable<any> {
		return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/signup`, learner);
	}

	_getUserProfileData(uid: string): Observable<any> {
	return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/get/${uid}`);
	}
	
	_getAllUsersByCollegeId():Observable<any>{
		return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/getLearnersListByClgId`);	
  	}

}
