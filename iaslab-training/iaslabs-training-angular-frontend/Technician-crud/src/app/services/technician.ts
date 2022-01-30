import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ThisReceiver } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { TechnicianModel } from '../models/technicianModels';
// here, put all methods that you need for provide technician
// like create, update, query or delete.
@Injectable({
  providedIn: 'root'
})

export class TechnicianService {
  //Observable:
  //Todos los que se suscriban a technician$ podrán obtener el technician que se emita con la función addTechnicianaEdit()
  private technician$ = new Subject<any>();

  baseUrl = 'http://localhost:4200/technician/';
  technicianList: TechnicianModel[] = [];

  // With HttpClient, you can use http methods like post, put, delete and get.
  constructor(private readonly http: HttpClient) { 
  }

  addTechnicianS(technician: TechnicianModel): void {
      this.technicianList.push(technician);
  }

  listadoTechnician(): TechnicianModel[] {
    return this.technicianList;
  }

  deleteTechnician(index: number): TechnicianModel[] {
    return this.technicianList.splice(index, 1);
  }

  //This methods are used for share data between components:
  addTechnicianEdit(technician: TechnicianModel) {
    //allow us emit this value
    this.technician$.next(technician);
  }

  getTechnicianEdit(): Observable<TechnicianModel> {
    //Transmite datos entre componentes. 
    //Permite a los otros compoenentes suscribirse:
    return this.technician$.asObservable();
  }

  editTechnicianService(technician: TechnicianModel): any {
    const index = this.technicianList.findIndex(technicianArrayService => technicianArrayService.id === technician.id);
    if(index !== -1)
      return this.technicianList.splice(index, 1 , technician);
    return;
  }

  getTechnicianById(id: number) {
    return this.http.get<TechnicianModel>(`${this.baseUrl}/${id}`);
  }

}