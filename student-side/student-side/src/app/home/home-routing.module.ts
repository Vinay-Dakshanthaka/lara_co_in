import { NgModel } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { HomeComponent } from './home.component';
import { CourseComponent } from '../course/course.component';
import { ActivateUserComponent } from './activate-user/activate-user.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { UnsubscribeUserComponent } from './unsubscribe-user/unsubscribe-user.component';
import { SubscribeUserComponent } from './subscribe-user/subscribe-user.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { PromotersComponent } from './promoters/promoters.component';
import { EnrollComponent } from './enroll/enroll.component';
import { HeroSectionNewComponent } from '../hero-section-new/hero-section-new.component';


const routes: Routes = [
    // {path: '', component: HomeComponent, pathMatch: 'full'},
    {path: '', component: HeroSectionNewComponent, pathMatch: 'full'},
    {path: 'coursedetails/:cpackId', component: CourseComponent},
    {path: 'activate/:userId/:actCode', component:ActivateUserComponent},
    {path: 'resetpwd/:userId/:actCode', component:ChangePasswordComponent},
    {path: 'forgotpwd', component:ForgotPasswordComponent},
    {path: 'unsubscribe/:email/:unsubsCode', component:UnsubscribeUserComponent},
    {path: 'subscribe/:email/:subsCode', component:SubscribeUserComponent},
    {path: 'contactUs', component: ContactFormComponent},
    {path: 'businessPromoter', component: PromotersComponent},
    {path: 'enroll', component: EnrollComponent},


    
];

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule{

}