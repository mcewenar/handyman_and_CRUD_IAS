import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceTechComponent } from './service-tech.component';

describe('ServiceTechComponent', () => {
  let component: ServiceTechComponent;
  let fixture: ComponentFixture<ServiceTechComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceTechComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceTechComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
