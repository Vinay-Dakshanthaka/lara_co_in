import { NgModel } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashComponent } from './admin-dash.component';
import { UploadComponent } from './components/upload/upload.component';
import { EditSubscriptionComponent } from './components/edit-subscription/edit-subscription.component';
import { BulkSubscriptionComponent } from './bulk-subscription/bulk-subscription.component';
import { PackageComponent } from './package/package.component';
import { PlacementdataComponent } from './placementdata/placementdata.component';
import { AddclassComponent } from './addclass/addclass.component';
import { AdduploadComponent } from './addupload/addupload.component';
import { AddcrudComponent } from './addcrud/addcrud.component';
import { CoursePackageComponent } from './components/course-package/course-package.component';




const routes: Routes = [
    { path: 'adashboard', component: AdminDashComponent },
    { path: 'admin/upload', component: UploadComponent },
    { path: 'edit-subs/:email', component: EditSubscriptionComponent },
    { path: 'admin/bulksubscription', component: BulkSubscriptionComponent },
    { path: 'batch', component: PackageComponent },
    { path: 'placements', component: PlacementdataComponent },
    { path: 'schedule', component: AddclassComponent },
    { path: 'uploadQuestions', component: AdduploadComponent },
    { path: 'manageQuestions', component: AddcrudComponent },
    { path: 'coursePackage', component: CoursePackageComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminDashRoutingModule {

}