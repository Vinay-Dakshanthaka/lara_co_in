import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestinfoComponent } from './testinfo.component';

describe('TestinfoComponent', () => {
  let component: TestinfoComponent;
  let fixture: ComponentFixture<TestinfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestinfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
