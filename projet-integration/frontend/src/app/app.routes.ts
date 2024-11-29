import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/agencylogin/login.component';
import { LandingComponent } from './landing-page/landing/landing.component';
import { TravelerloginComponent } from './auth/travelerlogin/travelerlogin.component';
import { SignupComponent } from './auth/agencylogin/signup/signup.component';
import { SignuptravelerComponent } from './auth/travelerlogin/signuptraveler/signuptraveler.component';
// start agency
import { AgencyhomeComponent } from './agency/pages/agencyhome/agencyhome.component';
import { EditoffersComponent } from './agency/pages/editoffers/editoffers.component';
import { AddofferComponent } from './agency/pages/addoffer/addoffer.component';
import { AgencyprofileComponent } from './agency/pages/agencyprofile/agencyprofile.component';
import { MessagesComponent } from './agency/pages/messages/messages.component';
import { NotificationsComponent } from './agency/pages/notifications/notifications.component';
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
          { path: 'agencyprofile', component: AgencyprofileComponent },
          { path: 'agencymessages', component: MessagesComponent },
          { path: 'agencynotification', component: NotificationsComponent },
        ],
      },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)], // Importing RouterModule with the defined routes
    exports: [RouterModule]                   // Export RouterModule for use in other modules
})
export class AppRoutingModule { }
