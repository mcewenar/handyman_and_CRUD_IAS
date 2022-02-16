import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { ReportModel } from '../models/reportModels';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  technicianList: ReportModel[] = [];
  baseUrl: string = 'http://localhost:8080/report';

  constructor(private readonly http: HttpClient) { }

  addReportS(report: ReportModel): Observable<ReportModel> {
    console.log(report.initDate);
    console.log(report.endDate);
    return this.http.post<ReportModel>(this.baseUrl, report);
  }
}
