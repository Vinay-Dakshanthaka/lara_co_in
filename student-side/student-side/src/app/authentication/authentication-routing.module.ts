import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationComponent } from './authentication.component';
import { SigninComponent } from './components/signin/signin.component';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
    { path: '', component: AuthenticationComponent,
        children: [
            { path: '', redirectTo: 'signin', pathMatch: 'full' },
            { path: 'signin', component: SigninComponent },
            { path: 'signup', component: SignupComponent }
        ]
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthenticationRoutingModule{

}