import { Component } from '@angular/core';
import * as AOS from 'aos';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  ngOnInit(): void {
    // Initialize AOS
    AOS.init({
      duration: 3000, // Animation duration in milliseconds
      once: true, // Whether animation should happen only once - while scrolling down
    });
  }

}
