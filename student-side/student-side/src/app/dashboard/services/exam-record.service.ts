import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConstantsService } from 'src/app/shared/services/constants.service';
import { CumulativeExamRecord } from '../classes/cumulativeExamRecord';

@Injectable({
  providedIn: 'root'
})
export class ExamRecordService {

  constructor(private http:HttpClient, private constantService:ConstantsService) { }

  _findAllExamRecords(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/examRecord/findAllExamRecords`);
  } 

  _getGrpExamScoreByLrId(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/examRecord/findGrpCompExamRecords`);
  }

  _getQuestionsList(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/findAll`);
  }

  _getQuestionsListForExam(videoId:string, microTopicId:string): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/findQues/${videoId}/${microTopicId}`);
  }

  _getCumQuestionsListForExam(microTopicIdsStr: string, totalQuestions:number): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/findCumulativeQues/${microTopicIdsStr}/${totalQuestions}`);
  }

  _getCumulativeExamRecord(cumExamRecordId:number): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/examRecord/findCumulativeExRecord/${cumExamRecordId}`);
  }
  _createExamCompletedRecord(examRecord: Object): Observable<any> {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/examRecord/addExamCompletedRecord`, examRecord);
  }

  _getAllCompExamRecords(exRecId:number):Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/examRecord/findAllCompExamRecords/${exRecId}`);
  }

  _getCumQuesAnsListByCexId(cumExamRecordId:number):Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/findCumQuesAnsByCexId/${cumExamRecordId}`);
  }

  _getQuesAnsListByCexId(cexId:number):Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/ques/findQuesAnsByCexId/${cexId}`);
  }
  
  _saveCumulativeExamRecord(cumExamRecord: CumulativeExamRecord) : Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/examRecord/saveCumulativeExamRecord`, cumExamRecord);
  }

  _updateCumulativeExamRecord(cumExamRecord: CumulativeExamRecord) : Observable<any>{
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/examRecord/updateCumulativeExamRecord`, cumExamRecord);
  }

  _findAllCumulativeExRecords(): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/examRecord/findAllCumulativeExRecords`);
  }

  _getMiTListByCumExRecId(cumExamRecordId:number): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/examRecord/findMiTListByCumExRecId/${cumExamRecordId}`);
  }

  _findWatchedMicroTopicIds(coursePackageId: number): Observable<any>{
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/examRecord/findWatchedMicroTopicIds/${coursePackageId}`);
  }
}
