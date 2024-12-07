import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  constructor(private http:HttpClient, private constantService: ConstantsService) { }

  _getSkillsList(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/skills/findAllSkills`);
  }

  _getSkillLevelList(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/skills/findAllSkillLevel`)
  }

  _updateLearnerSkills(learnerSkillData:any): Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/skills/addLearnerSkills`, learnerSkillData);
  }   

  _updateLearnerWorkExp(learnerWorkExpData:any): Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/workexp/addWorkExp`, learnerWorkExpData);
  }
}
