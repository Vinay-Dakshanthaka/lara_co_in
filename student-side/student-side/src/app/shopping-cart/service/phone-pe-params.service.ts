import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class PhonePeParamsService {

  constructor(
    private httpClient: HttpClient,
    private constantsService: ConstantsService
  ) { }

  getParams(data: any) {
    return this.httpClient.post(this.constantsService.ENDPOINT_BASE_URL + '/phonePe/v1/getParams', data).toPromise()
  }
}
