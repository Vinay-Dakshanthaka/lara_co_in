import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeroSectionNewComponent } from './hero-section-new.component';

describe('HeroSectionNewComponent', () => {
  let component: HeroSectionNewComponent;
  let fixture: ComponentFixture<HeroSectionNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeroSectionNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeroSectionNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
