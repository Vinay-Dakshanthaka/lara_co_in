import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShoppingCartRoutingModule } from './shopping-cart-routing.module';
import { BuyCoursesComponent } from './buy-courses/buy-courses.component';
import { CartComponent } from './cart/cart.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CartDetailsComponent } from './cart-details/cart-details.component';
import { BillSummaryComponent } from './bill-summary/bill-summary.component';
import { CheckoutComponent } from './checkout/checkout.component';


@NgModule({
  declarations: [BuyCoursesComponent, CartComponent, CartDetailsComponent, BillSummaryComponent, CheckoutComponent],
  imports: [
    CommonModule,
    ShoppingCartRoutingModule,
    FontAwesomeModule
  ],
  exports: [
    BuyCoursesComponent,
    CartComponent
  ]
})
export class ShoppingCartModule { }
