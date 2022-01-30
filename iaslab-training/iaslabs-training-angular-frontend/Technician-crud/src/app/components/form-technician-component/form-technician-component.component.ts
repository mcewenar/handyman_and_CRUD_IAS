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
  title = 'CREATE TECHNICIAN';
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

      //This disable ID input during the update*/
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

}














  //REPASOOOO (IGNORAR):
  /*technicianId: number;
  technicianName: string;
  technicianLastName: string;

  re = new RegExp('^[A-Za-z ]+$');

  wrongForm: boolean;
  wrongFormString: string;

  //Child to parent: https://angular.io/guide/inputs-outputs
  @Output() newTechnician = new EventEmitter<TechnicianModel>(); //NewEvent instances


  constructor() {
    this.technicianId = 0;
    this.technicianName = '';
    this.technicianLastName = '';

    this.wrongForm = false;
    this.wrongFormString = '';
  }

  ngOnInit(): void {
  }
  addTechnician() {
    if(this.technicianId === null || this.technicianName === '' || this.technicianLastName === '') {
      this.wrongForm = true;
      this.wrongFormString = 'All fields must be filled';
      return; //Don't return anything
    } else if (!this.re.test(this.technicianName) || !this.re.test(this.technicianLastName)) {
      this.wrongForm = true;
      this.wrongFormString = 'Fields Name and Last Name cannot be differents to letters';
      return;

    } else if(this.technicianId <= 0 ) {
      this.wrongForm = true;
      this.wrongFormString = 'Id cannot be less or equal than 0';
      return;
    }
    
    this.wrongForm = false;

    //Create Object:
    const TECHNICIAN: TechnicianModel = {
      id: this.technicianId,
      name: this.technicianName,
      lastName: this.technicianLastName
    }
    console.log("Child component:");
    console.log(TECHNICIAN);
    //Here, we pass new technician to parent component
    this.newTechnician.emit(TECHNICIAN);
    this.resetForm();
  }
  resetForm(): void {
    this.technicianId = 0;
    this.technicianName = '';
    this.technicianLastName = '';
  }
}*/
