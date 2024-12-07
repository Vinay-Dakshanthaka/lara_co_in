import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderComponent } from './order.component';
import { OrderReviewComponent } from './components/order-review/order-review.component';
import { OrderCompleteComponent } from './components/order-complete/order-complete.component';
import { OrderCancelComponent } from './components/order-cancel/order-cancel.component';

const routes: Routes = [
    {path:'order/complete/:orderId', component: OrderCompleteComponent},
    {path:'order/cancelled/:orderId', component: OrderCancelComponent}
];

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class OrderRoutingModule{

}