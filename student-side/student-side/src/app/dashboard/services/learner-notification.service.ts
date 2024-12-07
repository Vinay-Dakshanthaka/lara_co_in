import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class LearnerNotificationService {


  constructor(private http: HttpClient, private constantService: ConstantsService) { }
  _getLearnerNotificationsForCurrentUser(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/readAllLearnerNotificationsForCurrentUser`);
  } 

  _saveLearnerNotification(liveClassForm: FormGroup): Observable<any> {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/learner/saveLearnerNotification`, liveClassForm.value);
  }
  
  _readAllCoursePackages(){
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/readAllCoursePackages`);
  }
  
  _getAllLearnerNotifications(){
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/learner/readAllLearnerNotifications`);
  }
  
}
