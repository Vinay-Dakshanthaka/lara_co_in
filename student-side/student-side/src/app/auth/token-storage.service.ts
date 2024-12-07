import { LowerCasePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const USERID_KEY = 'UserId';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private roles: Array<string> = [];
  constructor(private router: Router) { }

  signOut() {
    //localStorage.removeItem(USERID_KEY);
    window.localStorage.clear();
    localStorage.removeItem('prevLinkTab');
    localStorage.setItem('_loggedIn', 'no')
    this.router.navigate([''], { queryParams: { logout: true } });
  }

  public saveToken(token: string) {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }

  public saveUsername(username: string) {
    window.localStorage.removeItem(USERNAME_KEY);
    window.localStorage.setItem(USERNAME_KEY, username);
  }

  public getUserId() {
    return window.localStorage.getItem(USERID_KEY);
  }
  public saveUserId(userId: string) {
    window.localStorage.setItem(USERID_KEY, userId);
  }
  public getUsername(): string {
    return localStorage.getItem(USERNAME_KEY);
  }

  public setLoggedInStatus(status: string) {
    localStorage.setItem('_loggedIn', status)
  }

  public getLoggedInStatus() {
    return (localStorage.getItem('_loggedIn') === 'yes' ? true : false) && !!this.getUserId();
  }

  public saveAuthorities(authorities: string[]) {
    window.localStorage.removeItem(AUTHORITIES_KEY);
    window.localStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public getAuthorities(): string[] {
    this.roles = [];

    if (localStorage.getItem(TOKEN_KEY) && localStorage.getItem(TOKEN_KEY) != "null") {
      JSON.parse(localStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        this.roles.push(authority.authority);
      });
    }

    return this.roles;
  }
}
