import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetWorkedHoursModel } from '../models/getWorkedHours';


@Injectable({
  providedIn: 'root'
})
export class HoursServiceService {
  baseUrl: string = 'http://localhost:8080/hours';

  constructor(private readonly http: HttpClient) { }

  getWorkedHoursService(idTech: string, numberWeek: number): Observable<GetWorkedHoursModel> {
    return this.http.get<GetWorkedHoursModel>(`${this.baseUrl}/${idTech}/${numberWeek}`);
  }

}
