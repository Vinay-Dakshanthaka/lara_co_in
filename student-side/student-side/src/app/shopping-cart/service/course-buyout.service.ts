import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class CourseBuyoutService {

  constructor(
    private http: HttpClient,
    private constants: ConstantsService
  ) { }

  private baseUrl = `${this.constants.ENDPOINT_BASE_URL}/coursebuyout/subscribelearner`;

  subscribeLearnerToPackages(data: any) {
    return this.http.put(this.baseUrl, data)
  }
}
