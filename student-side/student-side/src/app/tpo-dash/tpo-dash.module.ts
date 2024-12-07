import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TpoDashComponent } from './tpo-dash.component';
import { ClgUsersComponent } from './components/clg-users/clg-users.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { TpoService } from './services/tpo.service';
import { TpoDashRoutingModule } from './tpo-dash-routing.module';

@NgModule({
  declarations: [TpoDashComponent, ClgUsersComponent, UserDetailComponent],
  imports: [
    CommonModule,
    TpoDashRoutingModule
  ],
  providers: [TpoService]
})
export class TpoDashModule { }
