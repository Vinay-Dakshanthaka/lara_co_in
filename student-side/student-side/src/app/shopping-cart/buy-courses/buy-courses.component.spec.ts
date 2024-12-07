import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyCoursesComponent } from './buy-courses.component';

describe('BuyCoursesComponent', () => {
  let component: BuyCoursesComponent;
  let fixture: ComponentFixture<BuyCoursesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuyCoursesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuyCoursesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
