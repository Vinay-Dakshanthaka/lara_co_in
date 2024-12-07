import { NgModel } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TpoDashComponent } from './tpo-dash.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';



const routes: Routes = [   
    {path: 'tdashboard', component: TpoDashComponent},
    {path:'user-detail/:userId', component:UserDetailComponent}
];

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TpoDashRoutingModule{

}