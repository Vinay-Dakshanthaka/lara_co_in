import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlacementdataComponent } from './placementdata.component';

describe('PlacementdataComponent', () => {
  let component: PlacementdataComponent;
  let fixture: ComponentFixture<PlacementdataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlacementdataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlacementdataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
