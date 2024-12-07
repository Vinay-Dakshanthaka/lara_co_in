import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient, private constantService:ConstantsService) { }
  _getAllLearners(): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/getAllLearners`);
  }

  _getLearnerDetail(email:string): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/findUserDetailByEmail/${email}`);
  }

  _manageSubscription(mngSubsRequest: any) : Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/subscription/manageSubscription`, mngSubsRequest);
  }

  _getSubscribedPackages(userId:string): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/subscription/findUserSubscribedPkgs/${userId}`);
  }

  _sendBulkEmail(sendBulkEmailForm:any) : Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/subscription/sendBulkEmail`, sendBulkEmailForm);
  }

  _getAllTestimonials(): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/home/findTestimonials`);
  }

  _saveTestimonial(testimonialRequest:any): Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/admin/addTestimonial`, testimonialRequest);
  }

  _getTestimonialById(testimonialId:number): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/admin/findTestimonialById/${testimonialId}`);
  }

  _deleteTestimonialById(testimonialId:number): Observable<any>{
    return this.http.delete(`${this.constantService.ENDPOINT_BASE_URL}/admin/deleteTestimonialById/${testimonialId}`);
  }
}