import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PackagesRoutingModule } from './packages-routing.module';
import { JavaComponent } from './java/java.component';
import { AppRoutingModule } from '../app-routing.module';


@NgModule({
  declarations: [JavaComponent],
  imports: [
    CommonModule,
    PackagesRoutingModule,
    AppRoutingModule
  ],
  exports : [
     JavaComponent
  ]
})
export class PackagesModule { }
