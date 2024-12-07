import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from './shared/services/constants.service';
import { constant } from 'underscore';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  url: string = this.constants.ENDPOINT_BASE_URL;
  constructor(private http: HttpClient, private constants: ConstantsService) { }

  getselectedEntry(): Observable<any> {
    return this.http.get(this.url + '/admin/selectedPlacement');
  }

  getSelectedPackage() {
    return this.http.get<any>(this.url + '/admin/selectedPackage');
  }

  getSelectAll(): Observable<any> {

    return this.http.get(this.constants.BASE_URL_trimmed + 'class/getselectall');
  }

  PlacementDataSave(form: any) {
    return this.http.post(this.constants.BASE_URL_trimmed + 'api/admin/placement', form);
  }

  getPlacementData() {
    return this.http.get(this.constants.BASE_URL_trimmed + 'api/admin/selectPlacement');
  }

  updatePlacementData(data: any): Observable<any> {
    return this.http.put<any>(this.constants.BASE_URL_trimmed + 'api/admin/updatePlacement', data);
  }

  packageDataSave(form: any) {
    return this.http.post(this.constants.BASE_URL_trimmed + 'api/admin/package', form);
  }

  getPackageData() {
    return this.http.get(this.constants.BASE_URL_trimmed + 'api/admin/selectPackage');
  }

  updatePackagData(updatedData: any): Observable<any> {
    return this.http.put<any>(this.constants.BASE_URL_trimmed + 'api/admin/updatePackage', updatedData);
  }

  addClassRecord(record: any): Observable<any> {

    return this.http.post(this.constants.BASE_URL_trimmed + 'class/saveall', record);
  }


  updateclass(updateClass: any) {
    return this.http.put(this.constants.BASE_URL_trimmed + 'class/update', updateClass);
  }

  selectedEntry(data: any) {
    return this.http.post(this.constants.BASE_URL_trimmed + 'api/admin/placement/selected', data);
  }



  deleteRecord(index: any) {
    return this.http.delete(this.constants.BASE_URL_trimmed + 'class/delete/' + index);
  }

  uploadQuestions(data: any) {
    return this.http.post(this.constants.BASE_URL_trimmed + 'class/xlsxupload', data)
  }
}