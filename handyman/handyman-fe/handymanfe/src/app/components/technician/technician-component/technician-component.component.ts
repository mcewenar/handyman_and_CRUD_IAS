import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

import { TechnicianBack, TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-technician-component',
  templateUrl: './technician-component.component.html',
  styleUrls: ['./technician-component.component.css']
})
export class TechnicianComponentComponent implements OnInit {
  //Child to parent (edit):
  @Output() editEmitTech = new EventEmitter<TechnicianModel>();

  //Delete technician: child to parent:
  @Output() deleteEmitTech = new EventEmitter<string>();

  //EditTechnician: parent to child(edit):
  @Input() listTechnician: TechnicianModel[];

  //Dependency Injection (REMINDDD): 
  constructor(private _technicianService: TechnicianService, private toastr: ToastrService) {
    this.listTechnician = []; 
  }

  ngOnInit(): void {}

  /*editTechnician(technician: TechnicianModel): void {
    this._technicianService.addTechnicianEdit(technician);
  }*/

  deleteTechnicianById(id: string): void {
    this._technicianService.deleteTechnician(id).subscribe({next:
      (data: TechnicianModel) => {
        this.toastr.success(`Technician with ${id} deleted successful`,'Register');
        //Pass to parent component
        this.deleteEmitTech.emit(id);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.toastr.error('ERROR IN REQUEST','ERROR');
      },
      complete: () => {}
    });
  }

  editTechnician(technician: TechnicianModel): void {
    this._technicianService.editTechnicianService(technician.id, technician).subscribe({next:
      (data: TechnicianBack) => {
        this.toastr.success(`Technician with ${data.technician.id} edited successful`,'Register');
        //Pass to parent component
        this.editEmitTech.emit(data.technician);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
        this.toastr.error('ERROR IN REQUEST','ERROR');
      },
      complete: () => {}
    });
  }
}