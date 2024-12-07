import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from 'src/app/shared/services/constants.service';

import { Observable, Subject, BehaviorSubject } from 'rxjs';


@Injectable()
export class CompilecodeService {
    public notes: Subject<any> = new BehaviorSubject<any>(null);

    private eduBeasUrl = this.constantService.ENDPOINT_BASE_URL+'/education';
    private extApiUrl = 'https://api.judge0.com';
    compilerForm  = {
        "source_code":"",
        "language_id":"",
        "stdin":""
          
      };
      userId :any = 1;
    constructor(private http: HttpClient, private constantService:ConstantsService) { }

    getUserId() {
            return this.userId; 
    }
    getsubmissions(lang_id : any, code : string) {

        this.compilerForm.language_id = lang_id.language_id;
        this.compilerForm.source_code=code;
    
        const url = `${this.extApiUrl}/submissions/?base64_encoded=true&wait=false`;
        console.log("url:" + url);
        return this.http.post(url ,this.compilerForm );
    }
    
    getOutput(token:string){
      
        
      const url = `${this.extApiUrl}/submissions/${token}?base64_encoded=true`;
      console.log("from getOutput");
      console.log(url);
      
      return this.http.get(url );
    }
    
//     compilecode(data:string) {
//     	 console.log("I am from compilecode");
//         return this.http.post(`${this.apiUrl}/compilecode`, data);
//     }
    getsavecodes() {
        return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/programs`);
    }
    getlevels() {
        return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/level`);
    }
    gettopics() {
        return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/topic`);
    }
    getquestions(topicid:any,levelid:any) {
        return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/get/${topicid}/${levelid}`);
    }
    savecode(data:any) {
        return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/learner/save`, data);
    }
    savequestions(data:any) {
        return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/learner/saveLogic`, data);
    }
    
    updatecode(data:any,id:any) {
        return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}/learner/update/${id}`, data);
    }

    sendProgramData(data:any) {
        this.notes.next(data);
    }

    getProgramData(): Observable<any> {
        return this.notes.asObservable();
    }
   

   
}