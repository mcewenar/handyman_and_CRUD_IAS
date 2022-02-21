import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { FormTechnicianComponentComponent } from './form-technician-component.component';


//Describe component we are using
//Specific test using "f" before 
fdescribe('FormTechnicianComponentComponent', () => { //Callback or function inside parameter
  let component: FormTechnicianComponentComponent;
  let fixture: ComponentFixture<FormTechnicianComponentComponent>; //Creador de componentes
  let de: DebugElement;

  beforeEach(async () => { //Unit test configuration
    await TestBed.configureTestingModule({
      declarations: [ FormTechnicianComponentComponent ],
      providers: [],
      imports: []
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormTechnicianComponentComponent); //Get all from the component
    component = fixture.componentInstance;
    de = fixture.debugElement;
    fixture.detectChanges();
  });

  beforeAll(() => {

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should be render a title', () => {
    const title = de.query(By.css('h2'));

    //===
    expect(title.nativeElement.innerText).toBe('Fail');
  });

});

