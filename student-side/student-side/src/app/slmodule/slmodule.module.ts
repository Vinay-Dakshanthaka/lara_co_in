import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SlComponent } from './sl/sl.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SlmoduleRoutingModule } from './slmodule-routing.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [SlComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FontAwesomeModule
  ],
  exports: [
    SlComponent,
    SlmoduleRoutingModule
  ]
})
export class SlmoduleModule { }

