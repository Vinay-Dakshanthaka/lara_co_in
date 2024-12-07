import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from '../shared/services/constants.service';
import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CartServiceService {

  private _cartReferesh = new Subject<string>()

  cartRefresh = this._cartReferesh.asObservable()


  constructor(private http: HttpClient, private constantService: ConstantsService) { }

  refreshCart() {
    console.log('refersh cart')
    this._cartReferesh.next('change')
  }

  addToCart(data: any) {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/cart/addToCart`, data)
  }

  getCartNoOfItems(data: any) {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/cart/getCartNoOfItems`, data)
  }

  getUserCart(data: any) {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/cart/getUserCart`, data)
  }

  deleteFromCart(data: any) {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/cart/deleteFromCart`, data)
  }
}
