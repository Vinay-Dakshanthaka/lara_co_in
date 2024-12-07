import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user/user.component';
import { AdduploadComponent } from './addupload/addupload.component';
import { AddcrudComponent } from './addcrud/addcrud.component';
import { TestinfoComponent } from './testinfo/testinfo.component';

const routes: Routes = [
  {
    path:'addupload',component:AdduploadComponent
  },
  {
    path:'addquestioncrud',component:AddcrudComponent
  },
  {
    path:'usersave',component:UserComponent
  },
  {
    path : 'testinfo',component : TestinfoComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TestseriesRoutingModule { }
