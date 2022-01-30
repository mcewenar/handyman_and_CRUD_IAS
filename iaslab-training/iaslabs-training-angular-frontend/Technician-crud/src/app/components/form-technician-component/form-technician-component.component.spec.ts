import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormTechnicianComponentComponent } from './form-technician-component.component';

describe('FormTechnicianComponentComponent', () => {
  let component: FormTechnicianComponentComponent;
  let fixture: ComponentFixture<FormTechnicianComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormTechnicianComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormTechnicianComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
