import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { SharedService } from './services/shared.service';
import { FooterComponent } from './components/footer/footer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from '../user/services/user.service';
import { SharedComponent } from './shared.component';
import { StatusService } from './services/status.service';
import { SharedRoutingModule } from './shared-routing.module';
import { ShoppingCartModule } from '../shopping-cart/shopping-cart.module';
import { TermsAndCondComponent } from './components/footer/terms-and-cond/terms-and-cond.component';

@NgModule({
  declarations: [HeaderComponent, FooterComponent, SharedComponent, TermsAndCondComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    SharedRoutingModule,
    ShoppingCartModule
  ],
  exports: [
    HeaderComponent, FooterComponent
  ],
  providers: [
    SharedService,
    UserService,
    StatusService
  ]
})
export class SharedModule { }
