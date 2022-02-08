import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

import { TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-technician-component',
  templateUrl: './technician-component.component.html',
  styleUrls: ['./technician-component.component.css']
})
export class TechnicianComponentComponent implements OnInit {
  listTechnician: TechnicianModel[];


  //Dependency Injection (REMINDDD): 
  constructor(private _technicianService: TechnicianService, private toastr: ToastrService) {
    this.listTechnician = []; 
  }

  ngOnInit(): void {
    this.getTechnicians();
  }

  getTechnicians(): void {
    //Nos subscribimos:
    this._technicianService.getAllTechnician().subscribe(data => {
      console.log(data);
      this.listTechnician = data;
    })
    this.listTechnician;
  }
  /*getListTechnician(): void {
    this._technicianService.getAllTechnician().forEach((element: TechnicianModel) => {
      this.listTechnician.push({
        id: element.id,
        name: element.name,
        lastName: element.lastName
      })
    });
  }*/

  editTechnician(technician: TechnicianModel): void {
    this._technicianService.addTechnicianEdit(technician);
  }

  deleteTechnicianById(id: string): void {
    this._technicianService.deleteTechnician(id);
    this.toastr.show(`Technician has been eliminated succesful`,'Delete technician');
  }


}
  

































  
  /*listTechnician: TechnicianModel[];

  //Dependency Injection (REMINDDD): 
  constructor(private _technicianService: TechnicianService,
    private toastr: ToastrService) {
    this.listTechnician = []; 

  }
  //When initialize this component, we use this component
  ngOnInit(): void {
    this.listTechnician = this._technicianService.listadoTechnician();
  }
  getListTechnician(): void {
    this._technicianService.listadoTechnician().forEach((element: TechnicianModel) => {
      this.listTechnician.push({
        id: element.id,
        name: element.name,
        lastName: element.lastName
      })
    });
  }

  deleteTechnicianByIndex(index: number): void {
    this._technicianService.deleteTechnician(index);
    this.toastr.show(`Technician has been eliminated succesful`,'Delete technician');
  }

  editTechnician(technician: TechnicianModel): void {
    this._technicianService.addTechnicianEdit(technician);
  }
*/


