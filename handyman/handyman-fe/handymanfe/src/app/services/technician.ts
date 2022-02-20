import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { TechnicianBack, TechnicianModel } from '../models/technicianModels';
// here, put all methods that you need for provide technician
// like create, update, query or delete.
@Injectable({
  providedIn: 'root'
})

export class TechnicianService {
  private technician$ = new Subject<TechnicianModel>();
  technicianList: TechnicianModel[] = [];
  baseUrl: string = 'http://localhost:8080/technician';
  

  // With HttpClient, you can use http methods like post, put, delete and get.
  constructor(private readonly http: HttpClient) { 
  }

  getAllTechnician(): Observable<TechnicianModel[]> {
    return this.http.get<TechnicianModel[]>(this.baseUrl);

  }
  addTechnicianS(techinician: TechnicianModel): Observable<TechnicianBack> {
    return this.http.post<TechnicianBack>(this.baseUrl, techinician);
  }
  

  deleteTechnician(id: string): Observable<TechnicianModel> {
    return this.http.delete<TechnicianModel>(`${this.baseUrl}/${id}`);
  }

  editTechnicianService(id: string, technician: TechnicianModel): Observable<TechnicianBack> {
    return this.http.put<TechnicianBack>(`${this.baseUrl}/${id}`, technician);
  }

  //LIST:
  getTechnicians(technician: TechnicianModel[]) {
    //allow us emit this value
    this.technician$.asObservable();
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
  




}












































  //Observable:
  //Todos los que se suscriban a technician$ podrán obtener el technician que se emita con la función addTechnicianaEdit()
  /*private technician$ = new Subject<TechnicianModel>();

  baseUrl = 'http://localhost:8080';
  technicianList: TechnicianModel[] = [];

  

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

  editTechnicianService(technician: TechnicianModel): TechnicianModel {
    const index = this.technicianList.findIndex(technicianArrayService => technicianArrayService.id === technician.id);
    if(index !== -1)
      return this.technicianList.splice(index, 1 , technician);
    return;
  }

  getTechnicianById(id: number) {
    return this.http.get<TechnicianModel>(`${this.baseUrl}/${id}`);
  }*/

