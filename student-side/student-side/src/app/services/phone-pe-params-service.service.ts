import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from '../shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class PhonePeParamsServiceService {

  private phonePeUrl = this.constantService.ENDPOINT_BASE_URL + '/phonePe';
  constructor(
    private constantService: ConstantsService,
    private httpClient: HttpClient
  ) { }

  getParam(payload: String) {
    return this.httpClient.post(this.constantService.ENDPOINT_BASE_URL + '/getParams', payload)
  }
}
