import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadEmailidsForBulkEmailsComponent } from './upload-emailids-for-bulk-emails.component';

describe('UploadEmailidsForBulkEmailsComponent', () => {
  let component: UploadEmailidsForBulkEmailsComponent;
  let fixture: ComponentFixture<UploadEmailidsForBulkEmailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadEmailidsForBulkEmailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadEmailidsForBulkEmailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
