import { Component, OnInit } from '@angular/core';
import { faUser, faLock, faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { ActivatedRoute } from '@angular/router'

@Component({
  selector: 'app-sl',
  templateUrl: './sl.component.html',
  styleUrls: ['./sl.component.css']
})
export class SlComponent implements OnInit {

  //icons
  user = faUser;
  password = faLock;
  email = faEnvelope;

  //togglers
  hideLogin = false;


  constructor(private route: ActivatedRoute){
    console.log(this.route.snapshot.data['for'])
    if (this.route.snapshot.data['for'] === 'login'){
      this.hideLogin = false
    }
    else {
      this.hideLogin = true;
    }
    console.log(this.hideLogin)
  }

  ngOnInit() {
   
  }

}
