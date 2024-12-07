import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderComponent } from './order.component';
import { OrderReviewComponent } from './components/order-review/order-review.component';
import { OrderRoutingModule } from './order-routing.module';
import { OrderService } from './services/order.service';
import { OrderCompleteComponent } from './components/order-complete/order-complete.component';
import { OrderCancelComponent } from './components/order-cancel/order-cancel.component';

@NgModule({
  declarations: [OrderComponent, OrderReviewComponent, OrderCompleteComponent, OrderCancelComponent],
  imports: [
    CommonModule,
    OrderRoutingModule
  ],
  providers: [
	  OrderService
	]
})
export class OrderModule { }
