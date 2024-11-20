import { Component } from '@angular/core';
import { OffersService } from '../../../offers/offer.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-editoffers',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './editoffers.component.html',
})
export class EditoffersComponent {

  offers: any[] = [];
  errorMessage: string = '';

  constructor(private offersService: OffersService) {}

  ngOnInit(): void {
    this.fetchOffers();
  }

  fetchOffers(): void {
    this.offersService.getAllOffers().subscribe({
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
