import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class CouponService {
  constructor(private http: HttpClient, private constantService: ConstantsService) { }
  _saveCoupon(coupon: FormGroup): Observable<any> {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/learner/coupon`, coupon.value);
  }
  _readAllCoupons(){
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/learner/coupon/all`);
  }

}
