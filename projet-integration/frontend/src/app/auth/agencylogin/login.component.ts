import { Component } from '@angular/core';
import { AgenceService } from '../../agency/agence.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import * as AOS from 'aos';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,RouterModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  loginError: string = '';
  constructor(private agenceService: AgenceService, private router: Router) {}

  ngOnInit(): void {
    // Initialize AOS
    AOS.init({
      duration: 3000, // Animation duration in milliseconds
      once: true, // Whether animation should happen only once - while scrolling down
    });
  }

  onLogin(): void {
    console.log(this.email);
    console.log(this.password);
    if(this.email.length > 0 && this.password.length > 0){
      this.agenceService.loginAgence(this.email, this.password)
      .subscribe({
        next: (response) => {
          console.log(response);
          alert('Login successful ');
          const agenceDetails = response.agence;
          localStorage.setItem('agence', JSON.stringify(agenceDetails));
         
          this.router.navigate(['/agency/addoffer']);
        },
        error: (error) => {
          this.loginError = 'Invalid email or password';
          console.log(this.loginError);
          alert('invalid email or password');
        }
      });

    }
    else{
     alert('please enter email or password');
    }
    
  }



}
