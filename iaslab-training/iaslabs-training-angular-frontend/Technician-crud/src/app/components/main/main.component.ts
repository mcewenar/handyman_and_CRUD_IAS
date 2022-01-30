import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TechnicianModel } from 'src/app/models/technicianModels';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  
  constructor() { 
    
  }

  ngOnInit(): void {
  }
  
}
  
  
  
  
  
  
  
  
//repaso:  
/*id: number;
technicianList: TechnicianModel[] = [];


  //To use this service, we must inject it. 
  //private _technicianService: TechnicianService, private router: Router

  //Inject here the TechnicianService:
  constructor(private _technicianService: TechnicianService) { 
    this.id = 0;
  }

  ngOnInit(): void {
  }
  

  getTechnicianById() {
    this._technicianService.getTechnicianById(this.id).subscribe(data => {
      console.log(data);
    })
  }

  addTechnicianParent(technician: TechnicianModel) {
    console.log("I'm parent component: ");
    console.log(technician);
    this.technicianList.push(technician);
    console.log(this.technicianList);
  }
  deleteTechnicianParent(index: number) {
    this.technicianList.splice(index,1);
  }
*/

