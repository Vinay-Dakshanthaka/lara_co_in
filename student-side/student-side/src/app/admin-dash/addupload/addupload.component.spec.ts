import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdduploadComponent } from './addupload.component';

describe('AdduploadComponent', () => {
  let component: AdduploadComponent;
  let fixture: ComponentFixture<AdduploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdduploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdduploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
