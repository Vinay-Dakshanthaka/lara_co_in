import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { DashSideNavComponent } from './components/dash-side-nav/dash-side-nav.component';
import { DashboardService } from './services/dashboard.service';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { ProfileComponent } from './components/profile/profile.component';
import { UserProfileService } from './services/user-profile.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EducationService } from './services/education.service';
import { SkillService } from './services/skill.service';
import { MyCourseComponent } from './components/my-course/my-course.component';
import { ExamRecordComponent } from './components/exam-record/exam-record.component';
import { ExamRecordService } from './services/exam-record.service';
import { MyScoreComponent } from './components/my-score/my-score.component';
import { ScoreDetailComponent } from './components/score-detail/score-detail.component';
import { LogoutComponent } from './components/logout/logout.component';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';
import { ComExamComponent } from './components/com-exam/com-exam.component';
import { ComScoreComponent } from './components/com-score/com-score.component';
import { PointsComponent } from './components/points/points.component';
import { PointsService } from './services/points.service';
//import { OnlineCompilerModule } from '../online-compiler/online-compiler.module';
import { LiveClassComponent } from './components/live-class/live-class.component';
import { LearnerNotificationComponent } from './components/learner-notification/learner-notification.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [DashboardComponent, DashSideNavComponent, ProfileComponent, MyCourseComponent, 
    ExamRecordComponent, MyScoreComponent, ScoreDetailComponent, LogoutComponent, UpdatePasswordComponent, ComExamComponent, ComScoreComponent, PointsComponent, LiveClassComponent, LearnerNotificationComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    FontAwesomeModule
   // OnlineCompilerModule
  ],
  providers: [DashboardService, UserProfileService, ExamRecordService, PointsService]
})
export class DashboardModule { }
