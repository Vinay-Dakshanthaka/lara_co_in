import { TestBed } from '@angular/core/testing';

import { SignupDropdownService } from './signup-dropdown.service';

describe('SignupDropdownService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SignupDropdownService = TestBed.get(SignupDropdownService);
    expect(service).toBeTruthy();
  });
});
