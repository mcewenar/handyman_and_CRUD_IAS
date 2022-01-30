import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

//Components:
import { MainComponent } from './components/main/main.component';


//Here, we put the patchs
const routes: Routes = [
  {path: '', redirectTo: '/technician', pathMatch: 'full'},
  {path: 'technician', component: MainComponent},
  //{path: 'update-technician', component: AppComponent }
  {path: '**', redirectTo: '/technician', pathMatch: 'full'}
 

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
