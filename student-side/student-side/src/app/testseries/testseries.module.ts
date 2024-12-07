import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestseriesRoutingModule } from './testseries-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserComponent } from './user/user.component';
import { AddcrudComponent } from './addcrud/addcrud.component';
import { AdduploadComponent } from './addupload/addupload.component';
import { TestinfoComponent } from './testinfo/testinfo.component';


@NgModule({
  declarations: [
    AdduploadComponent,
    AddcrudComponent,
    UserComponent,
    TestinfoComponent
  ],
  imports: [
    CommonModule,
    TestseriesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports:[
    AdduploadComponent,
    AddcrudComponent,
    UserComponent,
    TestinfoComponent
  ]
})
export class TestseriesModule { }
