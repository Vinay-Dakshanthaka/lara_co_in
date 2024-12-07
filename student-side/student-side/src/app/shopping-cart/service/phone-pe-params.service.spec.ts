import { TestBed } from '@angular/core/testing';

import { PhonePeParamsService } from './phone-pe-params.service';

describe('PhonePeParamsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PhonePeParamsService = TestBed.get(PhonePeParamsService);
    expect(service).toBeTruthy();
  });
});
