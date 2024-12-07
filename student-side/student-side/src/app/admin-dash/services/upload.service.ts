import { HttpClient, HttpEvent, HttpErrorResponse, HttpEventType } from  '@angular/common/http';
import { map } from  'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Injectable({
    providedIn: 'root'
  })
  export class UploadService {

    constructor(private http: HttpClient, private constantService:ConstantsService) { }

    public upload(data, bulkUploadModel:number) {
        let uploadURL = `${this.constantService.ENDPOINT_BASE_URL}/ques/upload`;
        if(bulkUploadModel == 2){
          uploadURL = `${this.constantService.ENDPOINT_BASE_URL}/video/uploadVideoUrl`
        }
    
        return this.http.post<any>(uploadURL, data, {
          reportProgress: true,
          observe: 'events'
        }).pipe(map((event) => {
    
          switch (event.type) {
    
            case HttpEventType.UploadProgress:
              const progress = Math.round(100 * event.loaded / event.total);
              return { status: 'progress', message: progress };
    
            case HttpEventType.Response:
              return event.body;
            default:
              return `Unhandled event: ${event.type}`;
          }
        })
        );
      }

      public bulkUpload(data) {
        let uploadURL = `${this.constantService.ENDPOINT_BASE_URL}/bulksubscription/bulkupload`;
        return this.http.post<any>(uploadURL, data, {
          reportProgress: true,
          observe: 'events'
        }).pipe(map((event) => {    
          switch (event.type) {    
            case HttpEventType.UploadProgress:
              const progress = Math.round(100 * event.loaded / event.total);
              return { status: 'progress', message: progress };
    
            case HttpEventType.Response:
              return event.body;
            default:
              return `Unhandled event: ${event.type}`;
          }
        })
        );
      }

      uploademailIdsForBulkMessages(data) {
        let uploadURL = `${this.constantService.ENDPOINT_BASE_URL}/admin/emailIdsForBulkMessages`;
        return this.http.post<any>(uploadURL, data, {
          reportProgress: true,
          observe: 'events'
        }).pipe(map((event) => {    
          switch (event.type) {    
            case HttpEventType.UploadProgress:
              const progress = Math.round(100 * event.loaded / event.total);
              return { status: 'progress', message: progress };
    
            case HttpEventType.Response:
              return event.body;
            default:
              return `Unhandled event: ${event.type}`;
          }
        })
        );
      }
    public  getQuestionsList(): Observable<any> {
      return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/user/findq`);
    }
  }