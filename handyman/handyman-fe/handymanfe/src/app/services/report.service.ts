import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { ReportModel } from '../models/reportModels';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  technicianList: ReportModel[] = [];
  baseUrl = 'http://localhost:8080/report';

  constructor(private readonly http: HttpClient) { }

  addReport(report: ReportModel): Observable<any> {
    return this.http.post(this.baseUrl, report);
  }

}
