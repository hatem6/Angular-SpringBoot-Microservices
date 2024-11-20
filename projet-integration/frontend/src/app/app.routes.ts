import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/agencylogin/login.component';
import { LandingComponent } from './landing-page/landing/landing.component';
import { TravelerloginComponent } from './auth/travelerlogin/travelerlogin.component';
import { SignupComponent } from './auth/agencylogin/signup/signup.component';
import { SignuptravelerComponent } from './auth/travelerlogin/signuptraveler/signuptraveler.component';
import { AgencyhomeComponent } from './agency/pages/agencyhome/agencyhome.component';
import { EditoffersComponent } from './agency/pages/editoffers/editoffers.component';
import { AddofferComponent } from './agency/pages/addoffer/addoffer.component';
export const routes: Routes = [
    { path: '', component: LandingComponent },       // Default route should not have a leading slash
    { path: 'agencylogin', component: LoginComponent }, // Remove leading 
    { path: 'travelerlogin', component: TravelerloginComponent },
    { path: 'signupagency', component: SignupComponent },
    { path: 'signuptraveler', component: SignuptravelerComponent },
    {
        path: 'agency',
        component: AgencyhomeComponent, // Parent component
        children: [
          { path: 'editoffer', component: EditoffersComponent }, // Child route
          { path: 'addoffer', component: AddofferComponent },
        ],
      },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)], // Importing RouterModule with the defined routes
    exports: [RouterModule]                   // Export RouterModule for use in other modules
})
export class AppRoutingModule { }
