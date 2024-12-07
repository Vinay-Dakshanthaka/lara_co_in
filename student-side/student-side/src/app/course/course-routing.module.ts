import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderReviewComponent } from '../order/components/order-review/order-review.component';

const routes: Routes = [
    //{path: '', component: OrderComponent},
    {path: 'orderrev/:coursePackageId', component: OrderReviewComponent}
];

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CourseRoutingModule{

}