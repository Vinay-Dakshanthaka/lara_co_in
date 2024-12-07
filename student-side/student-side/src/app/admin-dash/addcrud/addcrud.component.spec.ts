import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddcrudComponent } from './addcrud.component';

describe('AddcrudComponent', () => {
  let component: AddcrudComponent;
  let fixture: ComponentFixture<AddcrudComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddcrudComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddcrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
