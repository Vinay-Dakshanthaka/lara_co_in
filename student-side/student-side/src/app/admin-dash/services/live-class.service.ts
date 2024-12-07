import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from 'src/app/shared/services/constants.service';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class LiveClassService {
  constructor(private http: HttpClient, private constantService: ConstantsService) { }
 
  _saveLiveClass(liveClassForm: FormGroup): Observable<any> {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/learner/saveLiveClass`, liveClassForm.value);
  }
  
  _readAllCoursePackages(){
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/readAllCoursePackages`);
  }
  
  _readAllLiveClasses(){
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/learner/readAllLiveClasses`);
  }
  _readAllLiveClassesForHomePage(){
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/auth/readAllLiveClassesForHomePage`);
  }

}
