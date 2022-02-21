import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
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
      declarations: [FormTechnicianComponentComponent],
      providers: [],
      imports: [ReactiveFormsModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormTechnicianComponentComponent); //Get all from the component
    component = fixture.componentInstance;
    de = fixture.debugElement;
    fixture.detectChanges(); //Detect changes each time and then renderize
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

  it('', ()=> {
    // A===B?
    const titleExpect = 'hello';
    expect(titleExpect).toBe('hello world');
    //To equal: compare between objects or arrays:
    const ObjectN = {name: 'Juan', lastName: 'Arango'};
    const ObjectN2 = {name: 'Juliana', lastName: 'Mejía'};
    //Have they the same reference?
    expect(ObjectN).toEqual(ObjectN2);
    //Compare booleans: TobeTrue or toBeFalse
    expect(true).toBeFalse();
    expect(false).toBeTrue();

    //To methods:
                      //(Which contain this method, method1); 
    const spyMethod = spyOn(component,'addOrEditTechnician');
    expect(spyMethod).toHaveBeenCalledWith();
    
  });

  //validate Form technician:
  describe('[Form validation]',() => {
    describe('Control "name"', () => {
      it('When this control has an empty value, must be invalid control - required',() => {
        //Pattern AAA
        const nameControl = component.form.get('name');
        const emptyValue = '';

        nameControl?.setValue(emptyValue);

        //expect(nameControl.errors.required);

      })

    })

  })
});
