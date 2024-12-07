import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
declare var $:any;
@Component({
  selector: 'app-admin-dash',
  templateUrl: './admin-dash.component.html',
  styleUrls: []
})
export class AdminDashComponent implements OnInit {

  constructor(private router:Router, private activatedRoute:ActivatedRoute) { }

  ngOnInit() {
    $(document).ready(function () {      
      var $a = $(".tabs li");
      $(".tabs li").on('click',function(){
        $a.removeClass("active");
        $(this).addClass("active");
      })
    });
  }

}
