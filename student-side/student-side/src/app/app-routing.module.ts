import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { ShowPlacementdataComponent } from './show-placementdata/show-placementdata.component';
import { ShowBatchdataComponent } from './show-batchdata/show-batchdata.component';
import { ShowScheduleComponent } from './show-schedule/show-schedule.component';
import { JavaComponent } from './packages/java/java.component';
import { DemoVideosComponent } from './demo-videos/demo-videos.component';


const routes: Routes = [
  {
    component: ShowPlacementdataComponent,
    path: "placementDataShow"
  },
  {
    component: ShowBatchdataComponent,
    path: "batchDataShow"
  },
  {
    component: ShowScheduleComponent,
    path: "scheduleShow"
  },
  {
    component: JavaComponent,
    path: "java"
  },
  {
    component: DemoVideosComponent,
    path: "demo-videos"
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
