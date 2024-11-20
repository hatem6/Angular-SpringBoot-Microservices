import { Component } from '@angular/core';
import { OffersService } from '../../../offers/offer.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-addoffer',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './addoffer.component.html',
})
export class AddofferComponent {
  agencyId: number = 1; // Replace with dynamic agency ID
  offer = {
    title: '',
    description: '',
    price: 0,
    localisation: '',
    type: '',
    theme: '',
    level: '',
    date: '',
    approvalStatus: false,
    etat: 'visible', // Default value
  };
  selectedFile: File | null = null;

  constructor(private offerService: OffersService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  addOffer(): void {
    const formData = new FormData();
    formData.append('agencyId', this.agencyId.toString());
    formData.append('title', this.offer.title);
    formData.append('description', this.offer.description);
    formData.append('price', this.offer.price.toString());
    formData.append('localisation', this.offer.localisation);
    formData.append('type', this.offer.type);
    formData.append('theme', this.offer.theme);
    formData.append('level', this.offer.level);
    formData.append('date', this.offer.date);
    formData.append('approvalStatus', this.offer.approvalStatus.toString());
    formData.append('etat', this.offer.etat);

    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }

    this.offerService.addOffer(formData).subscribe({
      next: () => {
        alert('Offer added successfully');
      },
      error: (error) => {
        console.error('Error adding offer:', error);
        alert('Failed to add offer');
      },
    });
  }
}
