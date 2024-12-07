import { NgModel } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';

import { ProfileComponent } from './components/profile/profile.component';
import { VideoPlaylistComponent } from '../course/components/video-playlist/video-playlist.component';
import { QuestionsComponent } from '../course/components/questions/questions.component';
import { ScoreDetailComponent } from './components/score-detail/score-detail.component';
import { MyCourseComponent } from './components/my-course/my-course.component';
import { QuesDetailComponent } from '../course/components/ques-detail/ques-detail.component';
import { ComExamComponent } from './components/com-exam/com-exam.component';
import { CumQuestionsComponent } from '../course/components/cum-questions/cum-questions.component';
import { componentFactoryName } from '@angular/compiler';
import { CumQuesDetailsComponent } from '../course/components/cum-ques-details/cum-ques-details.component';


const routes: Routes = [   
    {path: 'dashboard', component: DashboardComponent},
    {path: 'playlist/:coursePackageId/:courseId', component: VideoPlaylistComponent}, 
    {path: 'questions', component: QuestionsComponent},
    {path: 'score', component:ScoreDetailComponent},
    {path: 'mycourses', component:MyCourseComponent},
    {path: 'qdetails/:cexId', component:QuesDetailComponent},
    {path: 'cumquestions/:cumExamRecordId', component:CumQuestionsComponent},
    {path: 'cumqdetails/:cumExamRecordId', component: CumQuesDetailsComponent}
];

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DashboardRoutingModule{

}