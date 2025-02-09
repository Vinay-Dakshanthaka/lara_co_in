import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TermsAndCondComponent } from './terms-and-cond.component';

describe('TermsAndCondComponent', () => {
  let component: TermsAndCondComponent;
  let fixture: ComponentFixture<TermsAndCondComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TermsAndCondComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TermsAndCondComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
