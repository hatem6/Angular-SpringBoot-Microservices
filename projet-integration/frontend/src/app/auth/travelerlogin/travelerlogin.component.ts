import { Component } from '@angular/core';
import * as AOS from 'aos';
@Component({
  selector: 'app-travelerlogin',
  standalone: true,
  imports: [],
  templateUrl: './travelerlogin.component.html',
})
export class TravelerloginComponent {
  ngOnInit(): void {
    // Initialize AOS
    AOS.init({
      duration: 3000, // Animation duration in milliseconds
      once: true, // Whether animation should happen only once - while scrolling down
    });
  }
}
