import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { EventTypes } from 'src/app/models/event-types';
import { fromEvent } from 'rxjs';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent implements OnInit {

  @ViewChild('toastTemplate', { static: true }) toastEl!: ElementRef;

  @Output() disposeEvent = new EventEmitter()

  @Input() type: EventTypes

  @Input() message: string

  constructor() { }

  ngOnInit() {
    if (this.type == EventTypes.Error) {
      this.toastEl.nativeElement.classList.add('appear')
      setTimeout(() => {
        this.toastEl.nativeElement.classList.add('disappear')
        this.hide()
      }, 15000);
    }
    else {
      this.toastEl.nativeElement.classList.add('appear')
      setTimeout(() => {
        this.toastEl.nativeElement.classList.add('disappear')
        this.hide()
      }, 15000);
    }

    const button = this.toastEl.nativeElement.querySelector('.btn-close')

    fromEvent(button, 'click').pipe(take(1)).subscribe(() => {
      this.hide()
    })

  }

  hide() {
    this.toastEl.nativeElement.classList.add('disappear')
    setTimeout(() => {
      this.disposeEvent.emit()
    }, 1000);
  }

}
