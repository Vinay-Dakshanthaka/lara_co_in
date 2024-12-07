import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private orderBaseUrl = this.constantService._getBaseURL();

  constructor(private http: HttpClient, private constantService:ConstantsService) { }

  _startPayment(coursePackageId:number): Observable<any> {
    return this.http.get(`${this.orderBaseUrl}/startPayment/${coursePackageId}`);
  }

  _getPaymentDetails(orderId:string): Observable<any> {
    return this.http.get(`${this.orderBaseUrl}/payment/fetchPaymentDetails/${orderId}`);
  }

  _getCancelledOrderDetails(orderId:string): Observable<any> {
    return this.http.get(`${this.orderBaseUrl}/api/orderp/fetchOrderDetails/${orderId}`);
  }
}