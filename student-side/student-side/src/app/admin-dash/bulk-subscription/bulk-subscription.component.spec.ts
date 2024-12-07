import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkSubscriptionComponent } from './bulk-subscription.component';

describe('BulkSubscriptionComponent', () => {
  let component: BulkSubscriptionComponent;
  let fixture: ComponentFixture<BulkSubscriptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkSubscriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkSubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
