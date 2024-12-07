import { NgModel } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedComponent } from './shared.component';
import { CourseComponent } from '../course/course.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { TpoDashComponent } from '../tpo-dash/tpo-dash.component';
import { TermsAndCondComponent } from './components/footer/terms-and-cond/terms-and-cond.component';

const routes: Routes = [
    {path: 'dashboard', component: DashboardComponent},
    {path: 'tdashboard', component: TpoDashComponent},
    { path: 'termsAndCond', component: TermsAndCondComponent}
];

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SharedRoutingModule{

}