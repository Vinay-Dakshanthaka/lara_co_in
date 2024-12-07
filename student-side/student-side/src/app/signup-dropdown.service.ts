// signup-dropdown.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupDropdownService {
  private isOpenSubject = new BehaviorSubject<boolean>(false);
  isOpen$ = this.isOpenSubject.asObservable();

  openDropdown() {
    this.isOpenSubject.next(true);
  }

  closeDropdown() {
    this.isOpenSubject.next(false);
  }
}
