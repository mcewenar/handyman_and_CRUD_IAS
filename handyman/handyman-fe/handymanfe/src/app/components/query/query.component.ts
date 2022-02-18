import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { GetWorkedHoursModel } from 'src/app/models/getWorkedHours';
import { WeekTechnicianModel } from 'src/app/models/hoursModels';
import { HoursServiceService } from 'src/app/services/hours-service.service';

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css']
})
export class QueryComponent implements OnInit {

  regexString = '([A-Za-z0-9\-\_]+)';
  regexNumber = '^[0-9]*$';

  hours: GetWorkedHoursModel;
  hoursWorked: boolean;

  form: FormGroup;

  constructor(private fb: FormBuilder, private _hoursService: HoursServiceService, private toastr: ToastrService) {
    //In this way, we use forms with ReactiveForms:
    this.form = this.fb.group({
      idTechnician: ["",[Validators.required, Validators.minLength(36), Validators.maxLength(36), Validators.pattern(this.regexString)]],
      numberWeek: [null,[Validators.required, Validators.min(1), Validators.max(52), Validators.pattern(this.regexNumber)]]
    })

    this.hoursWorked = false;

    this.hours = {
      normalHours: 0, 
      nightHours: 0, 
      sundayHours: 0,
      extraNormalHours: 0,
      extraNightHours: 0,
      extraSundayHours: 0,
    };
  };

  ngOnInit(): void {}

  resetForm(): void {
    this.form.value.idTechnician = '';
    this.form.value.numberWeek = null;
    this.form.reset();
  }

  cleanRows(): void {
    this.hoursWorked = false;
  }

  getWorkedHours(): void {
    //Here, bring values from reactive form:
    const hours: WeekTechnicianModel = {
      technicianId: this.form.value.idTechnician,
      numberWeek: this.form.value.numberWeek
    }
    this._hoursService.getWorkedHoursService(hours.technicianId, hours.numberWeek).subscribe({next:
      (data: GetWorkedHoursModel) => {
        this.toastr.success(`Query successful`,'GET HOURS');
        this.hours = data;
        this.hoursWorked = true;
        this.resetForm();
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.error);
        this.toastr.error('ERROR IN REQUEST','ERROR');
      },
      complete: () => {}
    });
  }
    
}


