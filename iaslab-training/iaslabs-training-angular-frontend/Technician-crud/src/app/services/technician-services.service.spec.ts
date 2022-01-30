import { TestBed } from '@angular/core/testing';

import { TechnicianService } from './technician';

describe('TechnicianServicesService', () => {
  let service: TechnicianService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TechnicianService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
