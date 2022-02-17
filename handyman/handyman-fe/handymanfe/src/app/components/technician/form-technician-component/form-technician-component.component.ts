import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { TechnicianBack, TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-form-technician-component',
  templateUrl: './form-technician-component.component.html',
  styleUrls: ['./form-technician-component.component.css']
})
export class FormTechnicianComponentComponent implements OnInit {

  //Child to parent:
  @Output() saveEmit = new EventEmitter<TechnicianModel>();

  //EditTechnician: parent to child:
  @Input() editTechnicianInput: TechnicianModel;

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

    this.editTechnicianInput = {
                                  id: '', 
                                  name: '', 
                                  lastName: ''
                                }

    this.form.patchValue({
        id: this.editTechnicianInput.id,
        name: this.editTechnicianInput.name,
        lastName: this.editTechnicianInput.lastName,
    })
  };

  ngOnInit(): void {}

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
      console.log("entre a crear");      
      this.addTechnician();
    } else {
      console.log("entre a editar");     
      this.editTechnician();
    }
  }

  addTechnician(): void {
    //Here, bring values from html form:
    const technician: TechnicianModel = {
      id: this.form.value.id,
      name: this.form.value.name,
      lastName: this.form.value.lastName
    }

    //We call the Service METHOD for inject the dependency service on Constructor
    this._technicianService.addTechnicianS(technician).subscribe({next:
      (data: TechnicianBack) => {
        this.toastr.success(`Technician with id ${data.technician.id} has been created successful`,'Register');
        this.resetForm();
        //Pass to parent component
        this.saveEmit.emit(data.technician);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.toastr.error('ERROR IN REQUEST','ERROR');
      },
      complete: () => {}
    });
  }

  editTechnician(): void {
    console.log("EMIT TEST:", this.editTechnicianInput);
    this.editTechnicianInput = {
      //Here, bring values from html form:
      //id: this.form.getRawValue().id,
      id: this.form.value.id,
      name:  this.form.value.name,
      lastName: this.form.value.lastName
    }
    /*this._technicianService.editTechnicianService(TECHNICIAN.id,TECHNICIAN);
    this.toastr.info(`Technician has been updated succesful`,'Update technician');
    this.resetForm();
    console.log(TECHNICIAN);
    */
    
  }
}