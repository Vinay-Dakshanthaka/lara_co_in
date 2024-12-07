import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LearnerNotificationComponent } from './learner-notification.component';

describe('LearnerNotificationComponent', () => {
  let component: LearnerNotificationComponent;
  let fixture: ComponentFixture<LearnerNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LearnerNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LearnerNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
