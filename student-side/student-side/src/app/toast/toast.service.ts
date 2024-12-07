import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { EventTypes } from '../models/event-types';
import { ToastEvent } from '../models/toast-event';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  toastEvents: Observable<ToastEvent>;
  private _toastEvents = new Subject<ToastEvent>();

  constructor() {
    this.toastEvents = this._toastEvents.asObservable();
  }

  showSuccessToast(message: string) {
    console.log('from showSuccessToast')
    this._toastEvents.next({
      message,
      type: EventTypes.Success,
    });
  }
  
  showDangerToast(message: string) {
    this._toastEvents.next({
      message,
      type: EventTypes.Error,
    });
  }

}
