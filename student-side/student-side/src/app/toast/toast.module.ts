import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ToastRoutingModule } from './toast-routing.module';
import { ToastComponent } from './toast/toast.component';
import { ToasterComponent } from './toaster/toaster.component';


@NgModule({
  declarations: [ToastComponent, ToasterComponent],
  imports: [
    CommonModule,
    ToastRoutingModule
  ],
  exports: [
    ToastComponent,
    ToasterComponent
  ]
})
export class ToastModule { }
