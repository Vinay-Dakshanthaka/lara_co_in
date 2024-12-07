import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from './constants.service';

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  // BehaviorSubject to store UserName
  private currentUserNameStore = new BehaviorSubject<string>("");

  // Make UserName store Observable
  public currentUserName$ = this.currentUserNameStore.asObservable()
  
  constructor(private http: HttpClient, private constantService:ConstantsService) { }
  _getLoggedInUser(): Observable<any> {
		return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/getLoggedInUser`);
	}

  // Setter to update UserName
  _setCurrentUserName(userName: string) {
    this.currentUserNameStore.next(userName);
  }
  

  _login(learner: Object): Observable<any> {
		return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/login`, learner);
	}
}
