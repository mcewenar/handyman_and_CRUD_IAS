import { TestBed } from '@angular/core/testing';

import { ServiceTechService } from './service-tech.service';

describe('ServiceTechService', () => {
  let service: ServiceTechService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceTechService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
