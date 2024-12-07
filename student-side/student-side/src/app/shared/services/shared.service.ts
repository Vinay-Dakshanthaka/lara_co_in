import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from './constants.service';

@Injectable({
	providedIn: 'root'
})
export class SharedService {

	constructor(private http: HttpClient, private constantService: ConstantsService) { }

	_signUp(learner: Object): Observable<any> {
		return this.http.post(`${this.constantService.ENDPOINT_BASE_URL}` + `/learner/addLearner`, learner);
	}
}
