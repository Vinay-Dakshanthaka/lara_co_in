import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OnlineCompilerRoutingModule } from './online-compiler-routing.module';
import { OnlineCompilerComponent } from './online-compiler.component';
import { ProgrammingComponent } from './components/programming/programming.component';
import { SmartProgrammingComponent } from './components/smartprogramming/smartprogramming.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {TimeAgoPipe} from 'time-ago-pipe';
import { CompilecodeService } from './services/compilecode-service.service';

@NgModule({
  declarations: [OnlineCompilerComponent, ProgrammingComponent, SmartProgrammingComponent, TimeAgoPipe],
  imports: [
    CommonModule,
    OnlineCompilerRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [OnlineCompilerComponent, ProgrammingComponent, SmartProgrammingComponent, TimeAgoPipe],
  providers: [
    CompilecodeService
],
})
export class OnlineCompilerModule { }
