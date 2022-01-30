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
import { TechnicianComponentComponent } from './components/technician-component/technician-component.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FormTechnicianComponentComponent } from './components/form-technician-component/form-technician-component.component';
import { MainComponent } from './components/main/main.component';


@NgModule({
  declarations: [
    AppComponent,
    TechnicianComponentComponent,
    NavbarComponent,
    FormTechnicianComponentComponent,
    MainComponent
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
