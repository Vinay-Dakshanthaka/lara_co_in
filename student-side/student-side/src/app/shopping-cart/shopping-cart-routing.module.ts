import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BuyCoursesComponent } from './buy-courses/buy-courses.component';
import { CourseDetailComponent } from '../course/components/course-detail/course-detail.component';
import { CartDetailsComponent } from './cart-details/cart-details.component';


const routes: Routes = [
  {
    path: 'buyCourses',
    component: BuyCoursesComponent
  },
  {
    path: 'buyCourses/courseDetails/:coursePdfName',
    component: CourseDetailComponent
  },
  {
    path: 'cartDetails/courseDetails/:coursePdfName',
    component: CourseDetailComponent
  },
  {
    path: 'cartDetails',
    component: CartDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShoppingCartRoutingModule { }
