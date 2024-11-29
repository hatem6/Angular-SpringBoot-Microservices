import { Component } from '@angular/core';
import { AgenceService } from '../../../agency/agence.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule,RouterModule],
  templateUrl: './signup.component.html',
})
export class SignupComponent {
  name: string = '';
  email: string = '';
  password: string = '';
  selectedFile: File | null = null;

  constructor(private agenceService: AgenceService ,private router: Router) {}

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  signUp(): void {
    const formData = new FormData();
    formData.append('name', this.name);
    formData.append('email', this.email);
    formData.append('password', this.password);

    if (this.selectedFile) {
      formData.append('file', this.selectedFile);
    }
    
    console.log(this.selectedFile);

    this.agenceService.signUp(formData).subscribe(
      response => {
        console.log('Signup successful:', response);
        alert('Signup successful');
        this.router.navigate(['/agencylogin']);
      },
      error => {
        console.error('Signup error:', error);
      }
    );
  }
}
