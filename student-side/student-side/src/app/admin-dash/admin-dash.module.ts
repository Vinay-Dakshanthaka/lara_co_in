import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminDashComponent } from './admin-dash.component';
import { AdminDashRoutingModule } from './admin-dash-routing.module';
import { UploadService } from './services/upload.service';
import { UploadComponent } from './components/upload/upload.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SubscriptionComponent } from './components/subscription/subscription.component';
import { EditSubscriptionComponent } from './components/edit-subscription/edit-subscription.component';
import { MailingComponent } from './components/mailing/mailing.component';
import { CKEditorModule } from 'ckeditor4-angular';
import { TestimonialComponent } from './components/testimonial/testimonial.component';
import { UpcomingBatchComponent } from './components/upcoming-batch/upcoming-batch.component';
import { LiveClassComponent } from './components/live-class/live-class.component';
import { BulkSubscriptionComponent } from './bulk-subscription/bulk-subscription.component';
import { UploadEmailidsForBulkEmailsComponent } from './components/upload-emailids-for-bulk-emails/upload-emailids-for-bulk-emails.component';
import { LearnerNotificationComponent } from './components/learner-notification/learner-notification.component';
import { CouponComponent } from './components/coupon/coupon.component';
import { PackageComponent } from './package/package.component';
import { PlacementdataComponent } from './placementdata/placementdata.component';
import { AddclassComponent } from './addclass/addclass.component';
import { AdduploadComponent } from './addupload/addupload.component';
import { AddcrudComponent } from './addcrud/addcrud.component';
import { CoursePackageComponent } from './components/course-package/course-package.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [AdminDashComponent, UploadComponent, SubscriptionComponent, EditSubscriptionComponent, MailingComponent, TestimonialComponent, UpcomingBatchComponent, LiveClassComponent, BulkSubscriptionComponent, UploadEmailidsForBulkEmailsComponent, LearnerNotificationComponent, CouponComponent, PackageComponent, PlacementdataComponent, AddclassComponent, AdduploadComponent, AddcrudComponent, CoursePackageComponent],
  imports: [
    CommonModule,
    AdminDashRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    CKEditorModule,
    FontAwesomeModule
  ],
  providers: [UploadService]
})
export class AdminDashModule { }
