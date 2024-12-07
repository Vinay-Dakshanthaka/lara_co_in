import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import * as _ from 'underscore';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { faArrowUp } from '@fortawesome/free-solid-svg-icons';
//import { $ } from 'protractor';
declare var $: any;
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  upArrow = faArrowUp
  tab: string;
  role: string = 'ROLE_LEARNER';
  constructor(private router: Router, private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(queryParams => {
      this.tab = queryParams['tab'];
      if (this.tab != null) {
        //$('.tabs li a[href="#tab_e"]').click();   
        //$('.tabs li a[href="#tab_e"]').addClass("active"); 
      }
    });

    //$(document).ready(function () {      
    var $a = $(".tabs li");
    $(".tabs li").on('click', function () {
      $a.removeClass("active");
      $(this).addClass("active");
      localStorage.setItem('prevLinkTab', $(this).find('a').attr('href'));
    });
    if (localStorage.getItem('prevLinkTab') != null) {
      let tabHref = localStorage.getItem('prevLinkTab');
      $("[href='" + tabHref + "']").click();
      $("[href='" + tabHref + "']").addClass('active');
    }
    //});
    if (this.tokenStorage.getAuthorities().length > 0) {
      this.role = this.tokenStorage.getAuthorities()[0];
    }




  }

  logout() {
    this.authService._logout().subscribe(data => {
      this.tokenStorage.signOut();
    });
  }
}
