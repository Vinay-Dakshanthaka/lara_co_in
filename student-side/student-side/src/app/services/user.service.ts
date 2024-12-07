import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from '../shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = this.constantService.ENDPOINT_BASE_URL+'/user';
  private pmUrl = this.constantService.ENDPOINT_BASE_URL+'/test/pm';
  private adminUrl = this.constantService.ENDPOINT_BASE_URL+'/test/admin';

  constructor(private http: HttpClient, private constantService:ConstantsService) { }

  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, { responseType: 'text' });
  }

  getPMBoard(): Observable<string> {
    return this.http.get(this.pmUrl, { responseType: 'text' });
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }
}
