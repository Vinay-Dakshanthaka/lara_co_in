import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

class SubscribeUser{
  email: string;
  subscriptionCode: string;

  constructor(email: string, subscriptionCode:string){
    this.email = email;
    this.subscriptionCode = subscriptionCode;
  }
}

@Component({
  selector: 'app-subscribe-user',
  templateUrl: './subscribe-user.component.html',
  styleUrls: []
})
export class SubscribeUserComponent implements OnInit {
  email: string;
  subscriptionCode: string;

  subscribeUser: SubscribeUser;
  userSubscribed: boolean = false;
  exError: boolean = false;

  constructor(private router:Router, private activatedRoute: ActivatedRoute, private authService:AuthService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params=>{
      this.email = params['email'];     
      this.subscriptionCode = params['subsCode'];
    });

    

    this.subscribeUser = new SubscribeUser(this.email, this.subscriptionCode);
    this.authService._subscribeUser(this.subscribeUser)
      .subscribe(data=>{
        if(data.userSubscribed){
          this.userSubscribed = true;
        }else{
          this.exError = true;
        }
      })
  }

}