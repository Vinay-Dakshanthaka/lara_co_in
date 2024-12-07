import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';


@Injectable({
  providedIn: 'root'
})
export class TpoService {

  constructor(private http: HttpClient, private constantService:ConstantsService) { }
  _getLearnerCourseDetails(userId:string): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/getLrDetailForTpo/${userId}`);
  }

  _getLearnerCourseStats(userId:string): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/findLearnerCourseStats/${userId}`);
  }
}