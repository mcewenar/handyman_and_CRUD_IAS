import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  constructor(private fb: FormBuilder, private _technicianService: TechnicianService, private toastr: ToastrService) {}

  ngOnInit(): void {}
}
  

