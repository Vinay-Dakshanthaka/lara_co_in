import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  constructor(private http: HttpClient, private constantService: ConstantsService) { }

  _getUserProfileData(uid: string): Observable<any> {
    console.log(`${this.constantService.ENDPOINT_BASE_URL}/learner/profile/${uid}`)
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/profile/${uid}`);
  }

  _updateProfile(uid: string, learnerProfileData: any): Observable<any> {
    return this.http.put(`${this.constantService.ENDPOINT_BASE_URL}/learner/updateProfile/${uid}`, learnerProfileData);
  }

  _getAllCoursePackForLearner() {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/findAllCpackForLearner`);
  }

  _deleteEducationFormRow(eduId: number) {
    return this.http.delete(`${this.constantService.ENDPOINT_BASE_URL}/education/deleteEducation/${eduId}`);
  }

  _deleteSkillFormRow(skillId: number) {
    return this.http.delete(`${this.constantService.ENDPOINT_BASE_URL}/skills/deleteLearnerSkill/${skillId}`);
  }

  _deleteLearnerExpFormRow(workExpId: number) {
    return this.http.delete(`${this.constantService.ENDPOINT_BASE_URL}/workexp/deleteLearnerWorkExp/${workExpId}`);
  }
}
