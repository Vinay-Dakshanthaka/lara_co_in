import { NgModule } from '@angular/core';
import { HomeComponent } from './home.component';
import { HomeService } from './services/home.service';
import { ActivateUserComponent } from './activate-user/activate-user.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { UnsubscribeUserComponent } from './unsubscribe-user/unsubscribe-user.component';
import { SubscribeUserComponent } from './subscribe-user/subscribe-user.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { PromotersComponent } from './promoters/promoters.component';
import { MatTooltipModule as TooltipModule } from '@angular/material';
import { EnrollComponent } from './enroll/enroll.component';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';



@NgModule({
  declarations: [HomeComponent, ActivateUserComponent, ChangePasswordComponent, ForgotPasswordComponent, UnsubscribeUserComponent, SubscribeUserComponent, ContactFormComponent, PromotersComponent, EnrollComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TooltipModule
  ],
  providers: [
	  HomeService
	]
})
export class HomeModule { }
