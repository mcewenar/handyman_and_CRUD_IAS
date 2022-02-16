import { TestBed } from '@angular/core/testing';

import { HoursServiceService } from './hours-service.service';

describe('HoursServiceService', () => {
  let service: HoursServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HoursServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
