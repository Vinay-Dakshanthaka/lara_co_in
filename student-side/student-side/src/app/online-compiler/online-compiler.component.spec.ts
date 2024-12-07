import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OnlineCompilerComponent } from './online-compiler.component';

describe('OnlineCompilerComponent', () => {
  let component: OnlineCompilerComponent;
  let fixture: ComponentFixture<OnlineCompilerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OnlineCompilerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnlineCompilerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
