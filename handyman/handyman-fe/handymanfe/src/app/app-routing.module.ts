import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

//Components:
import { MainComponent } from './components/main/main.component';
import { QueryComponent } from './components/query/query.component';
import { ReportComponent } from './components/report/report.component';
import { TechnicianComponent } from './components/technician/technician.component';


//Here, we put the patchs
const routes: Routes = [
  {path: '', redirectTo: '/main', pathMatch: 'full'}, //matchea la url completa
  {path: 'report', component: ReportComponent},
  {path: 'main', component: MainComponent},
  {path: 'report/query', component: QueryComponent},
  {path: 'technician', component: TechnicianComponent},
  {path: '**', redirectTo: '/main', pathMatch: 'full'}
  //{path: 'update-technician', component: AppComponent }
 

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
