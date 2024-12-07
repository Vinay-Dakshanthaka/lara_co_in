import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddcrudComponent } from './addcrud.component';

describe('AddcrudComponent', () => {
  let component: AddcrudComponent;
  let fixture: ComponentFixture<AddcrudComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddcrudComponent]
    });
    fixture = TestBed.createComponent(AddcrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
