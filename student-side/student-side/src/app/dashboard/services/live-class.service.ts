import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from 'src/app/shared/services/constants.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LiveClassService {

  constructor(private http: HttpClient, private constantService: ConstantsService) { }
  _getLiveClasses(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/readAllLiveClassesForCurrentUser`);
  } 

  _getAllLiveClassesForHomePage(): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/auth/readAllLiveClassesForHomePage`);
  }

}
