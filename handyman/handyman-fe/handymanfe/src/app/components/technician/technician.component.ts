import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { TechnicianBack, TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-technician',
  templateUrl: './technician.component.html',
  styleUrls: ['./technician.component.css']
})
//Pattern smart component: 2 child, 1 parent
export class TechnicianComponent implements OnInit {

  technicianList: TechnicianModel[] = [];

  constructor(private readonly _technicianService: TechnicianService) { 
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

  editTechnician(technician: TechnicianModel): any {
    const index = this.technicianList.findIndex(technicianArrayService => technicianArrayService.id === technician.id);
    if(index !== -1)
      return this.technicianList.splice(index, 1 , technician);
    return;
  }
  


}