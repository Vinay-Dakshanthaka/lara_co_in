import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowPlacementdataComponent } from './show-placementdata.component';

describe('ShowPlacementdataComponent', () => {
  let component: ShowPlacementdataComponent;
  let fixture: ComponentFixture<ShowPlacementdataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowPlacementdataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowPlacementdataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
