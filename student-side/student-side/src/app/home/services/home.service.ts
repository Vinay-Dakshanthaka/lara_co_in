import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient, private constantService:ConstantsService) { }

  _getHomePageElements(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/home/findHomeElements`);
  }

   _getCollegeList(): Observable<any> {
     return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/home/findCollegeList`);
   }

   _getCoursePackages(): Observable<any>{
     return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/home/findCoursePackageList`)
   }
	_getAllNewBatches(): Observable<any> {
		return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}` + `/auth/findAllNewBatches`);
  }
  
  _getTestimonials(): Observable<any> {
		return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}` + `/home/findTestimonialsLmt`);
  }

  _getAllTestimonials(): Observable<any> {
		return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}` + `/home/findTestimonials`);
  }
 
  __enqury(enqueyForm: FormGroup): Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/home/enqury`, enqueyForm.value);
  }

  __businessPromoters(promotersForm: FormGroup): Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/home/businessPromoter`, promotersForm.value);
  }
}
