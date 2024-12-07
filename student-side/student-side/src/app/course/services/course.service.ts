import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TimeSpentOnVideo } from '../classes/timeSpentOnVideo';
import { ConstantsService } from 'src/app/shared/services/constants.service';


@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient, private constantService: ConstantsService) { }

  _getCoursePackage(id: number): Observable<any> {
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/findCoursePackage/${id}`);
  }

  _getCoursePackageList(): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/findAllC`);
  }

  _getCourseDetailsByCourseId(courseId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/findCourse/${courseId}`);
  }

  _getCourseListByCoursePkgId(coursePkgId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/findAllCourseByCpkg/${coursePkgId}`);
  }

  //topics
  _findAllTopicsByCourseId(courseId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/topic/findAllTopicsByCourseId/${courseId}`);
  }

  //questionsCount
  _findAllQuestionsCountByCourseId(courseId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/topic/findAllQuestionsCountByCourseId/${courseId}`);
  }

  //videos
  _findAllVideosByCourseId(coursePackageId, courseId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/video/findAllVideosByCourseId/${coursePackageId}/${courseId}`);
  }

  _findAllMaterialsByCourseId(courseId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/video/findAllMaterialsByCourseId/${courseId}`);
  }

  _downloadMaterial(courseId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/video/downloadMaterialFile/${courseId}`, { responseType: 'blob' });
  }





  _recordVideoActivity(timeSpentOnVideo: TimeSpentOnVideo): Observable<any> {
    return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/video/videoRec`, timeSpentOnVideo);
  }

  _checkCoursePackageSubscription(coursePackageId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/checkPSubscription/${coursePackageId}`);
  }

  _getCourseContentByCourseId(courseId: number): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/findCourseContentByCourseId/${courseId}`);
  }

  _getUserSubscriptionDetail(userId: string): Observable<any> {
    return this.http.get(`${this.constantService.ENDPOINT_BASE_URL}/learner/getUserSubscriptionDetail/${userId}`);
  }
  _getCouponCode(couponCode: string): Observable<any> {
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/learner/coupon/${couponCode}`);
  }

  _getUnsubscribedCoursesForUser(data: any) {
    return this.http.get<any>(`${this.constantService.ENDPOINT_BASE_URL}/learner/unsubscribedCourses/${data}`);
  }

  _updatePackage(data: any): Observable<any> {
    return this.http.put(`${this.constantService.ENDPOINT_BASE_URL}/cPackage/updatePackage`, data);
  }

}