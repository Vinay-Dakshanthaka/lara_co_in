import { TestBed } from '@angular/core/testing';

import { PhonePeParamsServiceService } from './phone-pe-params-service.service';

describe('PhonePeParamsServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PhonePeParamsServiceService = TestBed.get(PhonePeParamsServiceService);
    expect(service).toBeTruthy();
  });
});
