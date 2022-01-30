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

}












//REPASAR (IGNORAR):
  //Parent to child: decorator INPUT
  //@Input() technicianList: TechnicianModel[] = [];
  //child to parent: decorator INPUT
  //@Output() deleteTec = new EventEmitter<number>();

  /*constructor() { }

  ngOnInit(): void {
  }

  
  deleteTechnician(index: number) {
    //Pass the index to parent component
    this.deleteTec.emit(index);
    //console.log(index);
    //this.technicianList.splice(index, 1); //JS method for delete a list element since its index. .slice(init, until)
  }

  updateTechnician(technician: TechnicianModel, index: number): void {
    //this.technicianList[index].state = true;
    ////this.taskList[index].state = !task.state
    //console.log(this.taskList[index].state)

  }

}*/


