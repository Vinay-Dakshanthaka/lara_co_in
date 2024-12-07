import { TestBed } from '@angular/core/testing';

import { CourseBuyoutService } from './course-buyout.service';

describe('CourseBuyoutService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CourseBuyoutService = TestBed.get(CourseBuyoutService);
    expect(service).toBeTruthy();
  });
});
