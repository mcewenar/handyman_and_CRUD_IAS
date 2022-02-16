import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { TechnicianBack, TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-technician',
  templateUrl: './technician.component.html',
  styleUrls: ['./technician.component.css']
})
export class TechnicianComponent implements OnInit {

  technicianEdit: TechnicianModel;
  
  technicianList: TechnicianModel[] = [];

  constructor(private readonly _technicianService: TechnicianService) { 
    this.technicianEdit = {
                            id: '', 
                            name:'', 
                            lastName: ''
                          };
  }

  ngOnInit(): void {
    this.getTechnicians();
  }

  saveEmit(techinician: TechnicianModel): void {
    this.technicianList.push(techinician);
  }

  getTechnicians(): void {
    //Nos subscribimos:
    this._technicianService.getAllTechnician().subscribe(data => {
      console.log(data);
      this.technicianList = data;
    })
  }

  deleteEmitTechnician(id: string) {
    const index = this.technicianList.findIndex((technicianFromArray: TechnicianModel) => technicianFromArray.id === id);
    if(index !== -1)
      this.technicianList.splice(index, 1);
  }

  editEmit(technician: TechnicianModel): void {
    this.technicianEdit = technician;
    //console.log(this.technicianEdit);
    const index = this.technicianList.findIndex((technicianFromArray: TechnicianModel) => technicianFromArray.id === this.technicianEdit.id);
    if(index !== -1)
      console.log(index);
      this.technicianList.splice(index, 1 , this.technicianEdit);
    //Now, we're pass to child component form-technician-component using @input
  }


}