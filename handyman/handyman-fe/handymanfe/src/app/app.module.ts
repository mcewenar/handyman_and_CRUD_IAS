//Modules:
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';

//Toastr Angular module:
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';


//ReactiveForms Module: 
import { ReactiveFormsModule } from '@angular/forms';

//Components:
import { AppComponent } from './app.component';
import { TechnicianComponentComponent } from './components/technician/technician-component/technician-component.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FormTechnicianComponentComponent } from './components/technician/form-technician-component/form-technician-component.component';
import { MainComponent } from './components/main/main.component';
import { FooterComponent } from './components/footer/footer.component';
import { ReportComponent } from './components/report/report.component';
import { ServiceTechComponent } from './components/service-tech/service-tech.component';
import { TechnicianComponent } from './components/technician/technician.component';


@NgModule({
  declarations: [
    AppComponent,
    TechnicianComponentComponent,
    NavbarComponent,
    FormTechnicianComponentComponent,
    MainComponent,
    FooterComponent,
    ReportComponent,
    ServiceTechComponent,
    TechnicianComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
