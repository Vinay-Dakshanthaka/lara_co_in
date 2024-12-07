import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowBatchdataComponent } from './show-batchdata.component';

describe('ShowBatchdataComponent', () => {
  let component: ShowBatchdataComponent;
  let fixture: ComponentFixture<ShowBatchdataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowBatchdataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowBatchdataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
