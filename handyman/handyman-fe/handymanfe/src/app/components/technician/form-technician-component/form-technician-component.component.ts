import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-form-technician-component',
  templateUrl: './form-technician-component.component.html',
  styleUrls: ['./form-technician-component.component.css']
})
export class FormTechnicianComponentComponent implements OnInit {
  regexString = '^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1 ]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$'; //or regex = '^[A-Za-z ]+$';
  title = 'CREATE TECHNICIAN';

  form: FormGroup;

  technicianId: string;
  technicianName: string;
  technicianLastName: string;

  constructor(private fb: FormBuilder, private _technicianService: TechnicianService, private toastr: ToastrService) {
    //In this way, we use forms with ReactiveForms:
    this.form = this.fb.group({
      name: ["",[Validators.required, Validators.minLength(1), Validators.pattern(this.regexString)]],
      lastName: ["",[Validators.required, Validators.minLength(1), Validators.pattern(this.regexString)]]
    }) 
    this.technicianId = '';
    this.technicianName = '';
    this.technicianLastName = '';
  }

  ngOnInit(): void {
    //suscription for receive all change from service since beggin:
    this._technicianService.getTechnicianEdit().subscribe(data => {
      //information arrive here using .subscribe: from technician-component to form-technician
      this.title = `Edit technician with ID ${data.id}`;

      //method for edit form:
      this.form.patchValue({
        id: data.id,
        name: data.name,
        lastName: data.lastName,
      })
    })
  }

  resetForm(): void {
    this.technicianId = '';
    this.technicianName = '';
    this.technicianLastName = '';
    this.form.reset();
    this.title = 'Create technician';
  }


  addOrEditTechnician(): void {
    console.log(this.form);
    if(this.technicianId === '') {
      //create technician:
      this.addTechnician();
    } else {
      //Update techinician:
      this.editTechnician();
    }
  }

  addTechnician(): void {
    const TECHNICIAN: TechnicianModel = {
      //Here, bring values from html form:
      id: this.form.value.id,
      name: this.form.value.name,
      lastName: this.form.value.lastName
    }
    //We call the Service METHOD for inject the dependency service on Constructor
    this._technicianService.addTechnicianS(TECHNICIAN);
    this.toastr.success('Technician created successful','Register');
    this.resetForm();
  }

  editTechnician(): void {
    const TECHNICIAN: TechnicianModel = {
      //Here, bring values from html form:
      id: this.form.getRawValue().id,
      name:  this.form.value.name,
      lastName: this.form.value.lastName
    }
    this._technicianService.editTechnicianService(TECHNICIAN.id,TECHNICIAN);
    console.log(TECHNICIAN);
    this.toastr.info(`Technician with ID ${TECHNICIAN.id} has been updated succesful`,'Update technician');
    this.resetForm();

  }



}
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  /*title = 'CREATE TECHNICIAN';
  regexString = '^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1 ]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$'; //or regex = '^[A-Za-z ]+$';
  regexNumber = '^[0-9]+$';
  
  form: FormGroup;

  technicianId: number | undefined;
  technicianName: string;
  technicianLastName: string;

  //Dependency Injection (REMINDDD): 
  constructor(private fb: FormBuilder, private _technicianService: TechnicianService, private toastr: ToastrService) {
    //In this way, we use forms with ReactiveForms:
    this.form = this.fb.group({
      id: [null, [Validators.required, Validators.pattern(this.regexNumber)]],
      name: ["",[Validators.required, Validators.minLength(1), Validators.pattern(this.regexString)]],
      lastName: ["",[Validators.required, Validators.minLength(1), Validators.pattern(this.regexString)]]
    }) 
    this.technicianId = undefined;
    this.technicianName = '';
    this.technicianLastName = '';
  }

  ngOnInit(): void {
    //suscription for receive all change from service since beggin:
    this._technicianService.getTechnicianEdit().subscribe(data => {
      //information arrive here using .subscribe: from technician-component to form-technician
      this.technicianId = data.id;
      this.title = `Edit technician with ID ${data.id}`;

      //This disable ID input during the update
      this.form.controls['id'].disable();  //or using this.form.get('id')?.disable();

      //method for edit form:
      this.form.patchValue({
        id: data.id,
        name: data.name,
        lastName: data.lastName,
      })
    })
  }
  resetForm(): void {
    this.technicianId = undefined;
    this.technicianName = '';
    this.technicianLastName = '';
    this.form.reset();
    this.title = 'Create technician';
    this.form.controls['id'].enable();
  }

  addOrEditTechnician(): void {
    console.log(this.form);
    if(this.technicianId === undefined) {
      //create technician:
      this.addTechnician();
    } else {
      //Update techinician:
      this.editTechnician();
    }
  }

  addTechnician(): void {
    const TECHNICIAN: TechnicianModel = {
      //Here, bring values from html form:
      id: this.form.value.id,
      name: this.form.value.name,
      lastName: this.form.value.lastName
    }
    //We call the Service METHOD for inject the dependency service on Constructor
    this._technicianService.addTechnicianS(TECHNICIAN);
    this.toastr.success('Technician created successful','Register');
    this.resetForm();
  }

  editTechnician(): void {
    const TECHNICIAN: TechnicianModel = {
      //Here, bring values from html form:
      id: this.form.getRawValue().id,
      name:  this.form.value.name,
      lastName: this.form.value.lastName
    }
    this._technicianService.editTechnicianService(TECHNICIAN);
    console.log(TECHNICIAN);
    this.toastr.info(`Technician with ID ${TECHNICIAN.id} has been updated succesful`,'Update technician');
    this.resetForm();

  }
  */