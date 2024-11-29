import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AgenceService } from '../../agence.service';
@Component({
  selector: 'app-agencyprofile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './agencyprofile.component.html',
})
export class AgencyprofileComponent {
  profile = {
    id: 0,
    name: '',
    email: '',
    document:'',
    password:''
  };
  selectedFile: File | null = null; // Track selected file

  constructor(private agenceService: AgenceService) {}
  ngOnInit() {
    // Retrieve the agence object from localStorage
    const agence = localStorage.getItem('agence');

    // If agence exists in localStorage, parse it and extract the name
    if (agence) {
      try {
        const agenceData = JSON.parse(agence);
        if (agenceData && agenceData.name) {
          this.profile.id = agenceData.id;
          this.profile.name = agenceData.name;
          this.profile.email = agenceData.email;
          this.profile.document = agenceData.documentPath;
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
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  saveChanges() {
    if (!this.profile.id) {
      console.error('Agency ID is missing');
      return;
    }

    const formData = new FormData();
    formData.append('name', this.profile.name);
    formData.append('email', this.profile.email);
    formData.append('password', this.profile.password);
    if (this.selectedFile) {
      formData.append('file', this.selectedFile);
    }
    console.log(this.profile);
    this.agenceService.updateAgence(this.profile.id, formData).subscribe({
      next: (response) => {
        alert('Agence updated successfully');
        localStorage.setItem('agence', JSON.stringify(response.agence));
        window.location.reload();
      },
      error: (error) => {
        console.error('Error updating offer:', error);
        alert('the Email is alreay in use');
      },
    });
  }


}
