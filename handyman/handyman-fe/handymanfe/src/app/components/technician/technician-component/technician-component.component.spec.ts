import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicianComponentComponent } from './technician-component.component';

describe('TechnicianComponentComponent', () => {
  let component: TechnicianComponentComponent;
  let fixture: ComponentFixture<TechnicianComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TechnicianComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TechnicianComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
