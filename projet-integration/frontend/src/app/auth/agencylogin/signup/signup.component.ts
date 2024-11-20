import { Component } from '@angular/core';
import { AgenceService } from '../../../agency/agence.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './signup.component.html',
})
export class SignupComponent {
  name: string = '';
  email: string = '';
  password: string = '';
  selectedFile: File | null = null;

  constructor(private agenceService: AgenceService) {}

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
      },
      error => {
        console.error('Signup error:', error);
      }
    );
  }
}
