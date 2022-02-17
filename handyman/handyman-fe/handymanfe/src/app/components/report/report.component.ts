import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ReportModel } from 'src/app/models/reportModels';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  //Property binding dates:
  minDate: Date;
  maxDate: Date;

  //Report Model attributes:
  technicianId: string;
  serviceId: string;
  initDate: Date;
  endDate: Date;

  regexString = '([A-Za-z0-9\-\_]+)';

  form: FormGroup;

  constructor(private fb: FormBuilder, private _reportService:  ReportService, private toastr: ToastrService) {
    this.form = this.fb.group({
      technicianid: ['',[Validators.required, Validators.minLength(36), Validators.maxLength(36), Validators.pattern(this.regexString)]],
      serviceid: ['',[Validators.required, Validators.minLength(36), Validators.maxLength(36), Validators.pattern(this.regexString)]],
      initDate: ['',Validators.required],
      endDate: ['',Validators.required],
    },
    /*{
      Validators: this.validateDates()
    }*/
    
    )
    //this.ValidateDates()
    
    this.minDate = new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getHours());
    this.maxDate = new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getHours());

    this.technicianId = '';
    this.serviceId = '';
    this.initDate = new Date();
    this.endDate = new Date();

  }

  ngOnInit(): void {
    //2018-07-22
  }

  /*validateDates() {
    if (this.form.value.initDate >= this.form.value.endDate) {
      alert("hola");
      
    }
  }*/

  /*resetForm(): void {
    this.technicianId = '';
    this.serviceId = '';
    this.initDate = new Date();
    this.endDate = new Date();
    this.form.reset();
  }*/

  resetForm(): void {
    this.form.value.technicianid = '';
    this.form.value.serviceid = '';
    this.form.value.initDate = new Date();
    this.form.value.endDate = new Date();
    this.form.reset();
  }

  addReport(): void {
    //Here, bring values from html form:
    const report: ReportModel = {
      technicianId: this.form.value.technicianid,
      serviceId: this.form.value.serviceid,
      initDate: this.form.value.initDate,
      endDate: this.form.value.endDate
    }
    //|| (this.initDate >= this.endDate)
    if ((this.form.value.initDate >= this.form.value.endDate)) {
      alert(this.form.value.initDate >= this.form.value.endDate)
      
      this.toastr.error('End date cannot be less or equal than init date','ERROR');
      return;
    }
    
    //We call the Service METHOD for inject the dependency service on Constructor
    this._reportService.addReportS(report).subscribe({next: (data: ReportModel) => {
        this.toastr.success(`Report created successful`,'Register');
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





