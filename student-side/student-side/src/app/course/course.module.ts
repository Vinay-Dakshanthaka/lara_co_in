import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseComponent } from './course.component';
import { CoursePackComponent } from './components/course-pack/course-pack.component';
import { CourseService } from './services/course.service';
import { CoursePackDetailComponent } from './components/course-pack-detail/course-pack-detail.component';
import { CourseRoutingModule } from './course-routing.module';
import { CourseDetailComponent } from './components/course-detail/course-detail.component';
import { VgCoreModule } from 'videogular2/core';
import { VgControlsModule } from 'videogular2/controls';
import { VgBufferingModule } from 'videogular2/buffering';
import { VgOverlayPlayModule } from 'videogular2/overlay-play';
import { VideoPlaylistComponent } from './components/video-playlist/video-playlist.component';
import { QuestionsComponent } from './components/questions/questions.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { QuesDetailComponent } from './components/ques-detail/ques-detail.component';
import { CumQuestionsComponent } from './components/cum-questions/cum-questions.component';
import { CumQuesDetailsComponent } from './components/cum-ques-details/cum-ques-details.component';


@NgModule({
  declarations: [CourseComponent, CoursePackComponent, CoursePackDetailComponent, CourseDetailComponent, VideoPlaylistComponent, QuestionsComponent, QuesDetailComponent, CumQuestionsComponent, CumQuesDetailsComponent],
  imports: [
    CommonModule,
    CourseRoutingModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [CourseService]
})
export class CourseModule { }
