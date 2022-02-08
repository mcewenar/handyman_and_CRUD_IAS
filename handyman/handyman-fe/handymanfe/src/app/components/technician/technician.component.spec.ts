import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianComponent } from './technician.component';

describe('TechnicianComponent', () => {
  let component: TechnicianComponent;
  let fixture: ComponentFixture<TechnicianComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TechnicianComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TechnicianComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
