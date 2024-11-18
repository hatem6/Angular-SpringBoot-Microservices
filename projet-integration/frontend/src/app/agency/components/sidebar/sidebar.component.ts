import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent {
  isSidebarVisible = false; // Tracks the visibility of the sidebar
  agencyName = '';
  ngOnInit() {
    // Retrieve the agence object from localStorage
    const agence = localStorage.getItem('agence');

    // If agence exists in localStorage, parse it and extract the name
    if (agence) {
      try {
        const agenceData = JSON.parse(agence);
        if (agenceData && agenceData.name) {
          this.agencyName = agenceData.name;
        } else {
          console.error('Agency name is missing in localStorage data');
        }
      } catch (error) {
        console.error('Error parsing agence data from localStorage', error);
      }
    } else {
      console.log('No agence found in localStorage');
    }
  }

  toggleSidebar() {
    this.isSidebarVisible = !this.isSidebarVisible; // Toggle visibility
    console.log("Sidebar visibility toggled:", this.isSidebarVisible);
  }
}
