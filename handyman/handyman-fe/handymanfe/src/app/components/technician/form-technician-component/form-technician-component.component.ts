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

  //Child to parent: CREATE TECHNICIAN
  @Output() saveEmit = new EventEmitter<TechnicianModel>();
  //Child to parent: EDIT TECHNICIAN:
  @Output() editEmit = new EventEmitter<TechnicianModel>();
  
  regexString = '^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1 ]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$'; //or regex = '^[A-Za-z ]+$';
  title = 'CREATE TECHNICIAN';
  form: FormGroup;
  technicianId: string;

  constructor(private fb: FormBuilder, private _technicianService: TechnicianService, private toastr: ToastrService) {
    //In this way, we use forms with ReactiveForms:
    this.form = this.fb.group({
      name: ["",[Validators.required, Validators.minLength(1), Validators.pattern(this.regexString)]],
      lastName: ["",[Validators.required, Validators.minLength(1), Validators.pattern(this.regexString)]]
    }) 
    this.technicianId = '';
  };

  ngOnInit(): void {
    this.editNgOnInit();
  }

  resetForm(): void {
    this.technicianId = '';
    this.form.value.name = ''
    this.form.value.lastName = ''
    this.form.reset();
    this.title = 'Create technician';
  }

  addOrEditTechnician(): void {
    console.log(this.form);
    if(this.technicianId === '') {
      console.log("entré a crear");      
      this.addTechnician();
    } else {
      console.log("entré a editar");     
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

  editNgOnInit(): void {
    this._technicianService.getTechnicianEdit().subscribe(data => {
      //information arrive here using .subscribe: from technician-component to form-technician
      this.technicianId = data.id;
      this.title = `EDIT TECHNICIAN WITH ID ${data.id}`;
      //method for edit form:
      this.form.patchValue({
        id: data.id,
        name: data.name,
        lastName: data.lastName
      })
      this.technicianId = data.id;
    })
  }

  editTechnician(): void {
    const technicianEdit: TechnicianModel = {
      //Here, bring values from html form:
      id: this.technicianId,
      name:  this.form.value.name,
      lastName: this.form.value.lastName
    }

    this._technicianService.editTechnicianService(technicianEdit.id, technicianEdit).subscribe({next:
      (data: TechnicianBack) => {
        this.toastr.success(`Technician with ${data.technician.id} edited successful`,'Register');
        this.editEmit.emit(technicianEdit);
        this.resetForm();
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.toastr.error('ERROR IN REQUEST','ERROR');
      },
      complete: () => {}
    });
  }

}

