import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { ConstantsService } from 'src/app/shared/services/constants.service';

class UnsubscribeUser{
  email: string;
  unsubscriptionCode: string;

  constructor(email: string, unsubscriptionCode:string){
    this.email = email;
    this.unsubscriptionCode = unsubscriptionCode;
  }
}


@Component({
  selector: 'app-unsubscribe-user',
  templateUrl: './unsubscribe-user.component.html',
  styleUrls: []
})
export class UnsubscribeUserComponent implements OnInit {
  email:string;
  unsubscriptionCode:string;
  unsubscribeUser: UnsubscribeUser;
  userUnsubscribed: boolean = false;
  exError: boolean = false;
  subscriptionBaseUrl: string = '';
  constructor(private router:Router, private activatedRoute: ActivatedRoute, private authService:AuthService,
    private constantsService:ConstantsService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params=>{
      this.email = params['email'];     
      this.unsubscriptionCode = params['unsubsCode'];
    });

    this.subscriptionBaseUrl = this.constantsService._getBaseURL()+"/#/subscribe/"+this.email+"/"+this.unsubscriptionCode

    

    this.unsubscribeUser = new UnsubscribeUser(this.email, this.unsubscriptionCode);
    this.authService._unsubscribeUser(this.unsubscribeUser)
      .subscribe(data=>{
        if(data.userUnsubscribed){
          this.userUnsubscribed = true;
          this.authService._sendUnsubscriptionMail( new UnsubscribeUser(this.email, this.unsubscriptionCode)).subscribe(data=>{
            console.log(data);
          })
        }else{
          this.exError = true;
        }
      })
  }

}
