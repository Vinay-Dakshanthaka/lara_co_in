import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharedModule } from './shared/shared.module';
import { MaterialModule } from './material/material.module';
import { HomeModule } from './home/home.module';
import { CourseModule } from './course/course.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { AuthenticationModule } from './authentication/authentication.module';
import { OrderModule } from './order/order.module';
import { httpInterceptorProviders } from './auth/auth-interceptor';
import { AuthService } from './auth/auth.service';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { TpoDashModule } from './tpo-dash/tpo-dash.module';
import { AdminDashModule } from './admin-dash/admin-dash.module';
// import { DataComponent } from './data/data.component';
import { ShowPlacementdataComponent } from './show-placementdata/show-placementdata.component';
import { ShowBatchdataComponent } from './show-batchdata/show-batchdata.component';
import { ShowScheduleComponent } from './show-schedule/show-schedule.component';
import { PackagesModule } from './packages/packages.module';
import { TestseriesModule } from './testseries/testseries.module';
import { SlmoduleModule } from './slmodule/slmodule.module';
import { CompanyShowComponent } from './company-show/company-show.component';
// import { ReviewsComponent } from './reviews/reviews.component';
import { ShoppingCartModule } from './shopping-cart/shopping-cart.module';
import { ToastModule } from './toast/toast.module';
import { DemoVideosComponent } from './demo-videos/demo-videos.component';
import { HeroSectionNewComponent } from './hero-section-new/hero-section-new.component';
import { CourseDetailsComponent } from './course-details/course-details.component';
import { DiscoverSectionComponent } from './discover-section/discover-section.component';

@NgModule({
  declarations: [
    AppComponent,
    // DataComponent,
    ShowPlacementdataComponent,
    ShowBatchdataComponent,
    ShowScheduleComponent,
    CompanyShowComponent,
    // ReviewsComponent,
    DemoVideosComponent,
    HeroSectionNewComponent,
    CourseDetailsComponent,
    DiscoverSectionComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    HomeModule,
    CourseModule,
    DashboardModule,
    TpoDashModule,
    AdminDashModule,
    AuthenticationModule,
    OrderModule,
    PackagesModule,
    TestseriesModule,
    SlmoduleModule,
    ShoppingCartModule,
    ToastModule,
  ],
  providers: [
    httpInterceptorProviders, AuthService,
    { provide: LocationStrategy, useClass: HashLocationStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
