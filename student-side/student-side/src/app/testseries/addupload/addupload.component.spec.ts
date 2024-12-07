import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdduploadComponent } from './addupload.component';

describe('AdduploadComponent', () => {
  let component: AdduploadComponent;
  let fixture: ComponentFixture<AdduploadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdduploadComponent]
    });
    fixture = TestBed.createComponent(AdduploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
