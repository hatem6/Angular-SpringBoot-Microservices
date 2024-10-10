import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/agencylogin/login.component';
import { LandingComponent } from './landing-page/landing/landing.component';
import { TravelerloginComponent } from './auth/travelerlogin/travelerlogin.component';

export const routes: Routes = [
    { path: '', component: LandingComponent },       // Default route should not have a leading slash
    { path: 'agencylogin', component: LoginComponent }, // Remove leading 
    { path: 'travelerlogin', component: TravelerloginComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)], // Importing RouterModule with the defined routes
    exports: [RouterModule]                   // Export RouterModule for use in other modules
})
export class AppRoutingModule { }
