import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
declare var $:any;

@Component({
  selector: 'app-tpo-dash',
  templateUrl: './tpo-dash.component.html',
  styleUrls: ['./tpo-dash.component.css']
})
export class TpoDashComponent implements OnInit {

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
    private authService:AuthService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    $(document).ready(function () {      
      var $a = $(".tabs li");
      // $a.click(function () {
      //   $a.removeClass("active");
      //   $(this).addClass("active");
      // });

      $(".tabs li").on('click',function(){
        $a.removeClass("active");
        $(this).addClass("active");
      })
    });
  }

}
