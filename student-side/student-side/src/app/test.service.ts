import { Injectable } from '@angular/core';
import{HttpClient} from '@angular/common/http'
import { ConstantsService } from './shared/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class TestService {

  baseUrl = this.constants.BASE_URL_trimmed;
  constructor(private http : HttpClient, private constants: ConstantsService) { }
  addtest(file: any) {

    return this.http.post( this.baseUrl + "question/save", file);
  }

  getFetchedQuestions(){
    return this.http.get(this.baseUrl + "question/fetch")
  }

  getFetchedDefualtQuestions(data:any){
    return this.http.post(this.baseUrl + "question/questiondetails",data);
  }

  deleteRecord(data:any){
    return this.http.post(this.baseUrl + "question/delete", data)
  }
  updateQuestion(data:any){
    return this.http.put(this.baseUrl + "question/update",data)
  }

  // userSave(User:any){
  //   return this.http.post(this.baseUrl + "user/save",User)
  // }

  userSave(User:any){
    return this.http.post(this.baseUrl+"user/save",User)
  }

  getfetch(){
    return this.http.get(this.baseUrl + "user/fetchuserquestions")
  }

  testevaluate(Data:any){
    return this.http.post(this.baseUrl + "user/evaluate",Data)
  }

  userTestCheck(email:any){
    return this.http.post(this.baseUrl + "user/usercheck",email)
  }
}
