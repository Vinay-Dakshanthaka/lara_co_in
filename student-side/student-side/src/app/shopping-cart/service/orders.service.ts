import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(
    private httpClient: HttpClient,
    private constantService: ConstantsService
  ) { }

  initiateOrder(data: any) {
    return this.httpClient.post(this.constantService.ENDPOINT_BASE_URL + '/orders/v2/initiate', data).toPromise()
  }

  checkStatus(data: any) {
    return this.httpClient.post(this.constantService.ENDPOINT_BASE_URL + '/orders/v2/checkStatus', data)
  }
}
