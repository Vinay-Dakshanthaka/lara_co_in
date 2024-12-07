import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

class ActivateUser{
  userId: string;
  activationCode: string;

  constructor(userId: string, activationCode: string){
    this.userId = userId;
    this.activationCode = activationCode;
  }
}

@Component({
  selector: 'app-activate-user',
  templateUrl: './activate-user.component.html',
  styleUrls: []
})
export class ActivateUserComponent implements OnInit {
  userId: string;
  activationCode: string;
  activateUser: ActivateUser;
  userActivated: boolean = false;
  exError: boolean = false;
  constructor(private router:Router, 
    private activatedRoute: ActivatedRoute,
    private authService:AuthService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params=>{
        this.userId = params['userId'];
        this.activationCode = params['actCode'];
    });
    this.activateUser = new ActivateUser(this.userId, this.activationCode);
    this.authService._activateUser(this.activateUser)
      .subscribe(data=>{
        if(data.userActivated){
          this.userActivated = true;
        }else{
          this.exError = true;
        }
      });
  }

}
