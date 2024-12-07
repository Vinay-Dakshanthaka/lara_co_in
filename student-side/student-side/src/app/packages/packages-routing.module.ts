import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { JavaComponent } from './java/java.component';

const routes: Routes = [
  {
    component : JavaComponent,
    path : "java"
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PackagesRoutingModule { }
