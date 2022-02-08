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
  
  