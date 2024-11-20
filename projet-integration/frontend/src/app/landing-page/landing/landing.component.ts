import { Component,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Add CommonModule for Angular directives
import { RouterModule } from '@angular/router';
import * as AOS from 'aos'; 
@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule,RouterModule], // Import CommonModule
  templateUrl: './landing.component.html',
})
export class LandingComponent implements OnInit {
  ngOnInit(): void {
    // Initialize AOS
    AOS.init({
      duration: 3000, // Animation duration in milliseconds
      once: true, // Whether animation should happen only once - while scrolling down
    });
  }

  isOpen: boolean = false;
  isDropDownOpen: boolean = false;
  isSecondDropDownOpen: boolean = false;

  stats = [
    { data: '3K', title: 'Clients' },
    { data: '20+', title: 'Agencies' },
    { data: '14+', title: 'Countries' },
    { data: '120+', title: 'Trips' }
  ];

  // Define the menuItems property
  menuItems = [
    { title: 'About', path: '/#about' },
    { title: 'Services', path: '/#services' },
    { title: 'Contact', path: '/#contact' },
  ];

  toggleMenu(): void {
    this.isOpen = !this.isOpen;
  }
  toggleDropDown(): void {
    this.isDropDownOpen = !this.isDropDownOpen;
  }
  toggleSecondDropDown(): void {
    this.isSecondDropDownOpen = !this.isSecondDropDownOpen;
  }
}
