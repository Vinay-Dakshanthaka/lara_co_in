import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html'
})
export class LogoutComponent implements OnInit {  
  constructor(private router:Router, private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private tokenStorage:TokenStorageService) { }

  ngOnInit() {   
  }

  logout() {
    this.authService._logout().subscribe(data=>{
        this.tokenStorage.signOut();			
    });		
  }
}