import { Component, OnInit } from '@angular/core';
import { TechnicianService } from 'src/app/services/technician';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private _technicianService: TechnicianService) { }

  ngOnInit(): void {
  }
  
  searchById(): void {
    //...
    console.log("searching...");

  }

}
