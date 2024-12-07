import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ToastEvent } from 'src/app/models/toast-event';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-toaster',
  templateUrl: './toaster.component.html',
  styleUrls: ['./toaster.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ToasterComponent implements OnInit {

  currentToasts: ToastEvent[] = []

  constructor(
    private toastService: ToastService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.subscribeToast()
  }

  subscribeToast() {
    this.toastService.toastEvents.subscribe(toast => {
      const currentToast: ToastEvent = {
        message: toast.message,
        type: toast.type
      }
      this.currentToasts.push(currentToast)
      console.log(this.currentToasts)
      this.cdr.detectChanges()
    })
  }


  dispose(index: number) {
    this.currentToasts.splice(index, 1)
    this.cdr.detectChanges()
  }
}
