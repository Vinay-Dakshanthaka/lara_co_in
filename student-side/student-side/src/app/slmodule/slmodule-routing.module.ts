import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SlComponent  } from './sl/sl.component';

const routes: Routes = [
  {
    component : SlComponent,
    path : "signuplogin/login",
    data: {for : 'login'}
  },
  {
    component : SlComponent,
    path : "signuplogin/signup",
    data: {for : 'signup'}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SlmoduleRoutingModule { }