import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class EducationService {
  private eduBeasUrl = this.constantService.ENDPOINT_BASE_URL+'/education';

  constructor(private http:HttpClient, private constantService:ConstantsService) { }

  _getEducationList(): Observable<any> {
    return this.http.get(`${this.eduBeasUrl}/findAllEdu`);
  }

  _getEduSpecList(): Observable<any> {
    return this.http.get(`${this.eduBeasUrl}/findAllEduSpec`)
  }
  _getEduSpecListByEduId(eduId:number): Observable<any>{
    return this.http.get(`${this.eduBeasUrl}/findAllEduSpecByEduId/${eduId}`);
  }

  _updateLearnerEdu(learnerEducationData:any): Observable<any>{
    return this.http.post(`${this.eduBeasUrl}/addLearnerEducation`, learnerEducationData);
  }   
}
