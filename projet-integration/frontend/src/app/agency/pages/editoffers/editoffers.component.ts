import { Component } from '@angular/core';
import { OffersService } from '../../../offers/offer.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-editoffers',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './editoffers.component.html',
})
export class EditoffersComponent {

  offers: any[] = [];
  errorMessage: string = '';
  isModalOpen = false;
  selectedOffer: any = {};
  agencyId: number = 0;
  selectedFile: File | null = null;
  constructor(private offersService: OffersService) {}

  ngOnInit(): void {
    // Retrieve the agence object from localStorage
    const agence = localStorage.getItem('agence');

    // If agence exists in localStorage, parse it and extract the name
    if (agence) {
      try {
        const agenceData = JSON.parse(agence);
        if (agenceData && agenceData.name) {
          this.agencyId = agenceData.id;
        } else {
          console.error('Agency name is missing in localStorage data');
        }
      } catch (error) {
        console.error('Error parsing agence data from localStorage', error);
      }
    } else {
      console.log('No agence found in localStorage');
    }
    this.fetchOffers();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  openModal(offerId: string): void {
    this.selectedOffer = this.offers.find((offer) => offer.id === offerId);
    this.isModalOpen = true;
  }

  onUpdateOffer(): void {
    const formData = new FormData();
    formData.append('agencyId', this.agencyId.toString());
    formData.append('title',this.selectedOffer.title);
    formData.append('description', this.selectedOffer.description);
    formData.append('price', this.selectedOffer.price.toString());
    formData.append('localisation', this.selectedOffer.localisation);
    formData.append('type', this.selectedOffer.type);
    formData.append('theme',this.selectedOffer.theme);
    formData.append('level',  this.selectedOffer.level);
    formData.append('date',this.selectedOffer.date);
    formData.append('approvalStatus', this.selectedOffer.approvalStatus.toString());
    formData.append('etat', this.selectedOffer.etat);
  
    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }
  
    // Use the ID from the selectedOffer to send the update
    this.offersService.updateOffer(this.selectedOffer.id, formData).subscribe({
      next: () => {
        alert('Offer updated successfully');
        this.fetchOffers(); // Refresh the offers list
      },
      error: (error) => {
        console.error('Error updating offer:', error);
        alert('Failed to update offer');
      },
    });
  
    this.closeModal();
  }
  

  closeModal(): void {
    this.isModalOpen = false;
    this.selectedOffer = {};
  }
  deleteOffer(offerId: any): void {
    const confirmation = window.confirm('Are you sure you want to delete this offer?');
    if (confirmation) {
      this.offersService.deleteOffer(offerId).subscribe({
        next: () => {
          alert('Offer deleted successfully');
          this.fetchOffers(); // Refresh the offers list
        },
        error: (error) => {
          console.error('Error deleting offer:', error);
          alert('Failed to delete offer');
        },
      });
    }
  }
  

  fetchOffers(): void {
    console.log(this.agencyId);
    this.offersService.getAllOffersByAgencyId(this.agencyId).subscribe({
      next: (data) => {
        this.offers = data;
        console.log('Offers fetched successfully:', data);
      },
      error: (error) => {
        this.errorMessage = 'Failed to load offers.';
        console.error('Error fetching offers:', error);
      },
    });
  }

}
