import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OffersService {
  private apiUrl = 'http://localhost:8888/api/offers'; // Replace with your actual API URL

  constructor(private http: HttpClient) {}

  getAllOffers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  getAllOffersByAgencyId(agencyId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/agency/${agencyId}`);
  }
  addOffer(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, formData);
  }
  updateOffer(id: number, formData: FormData): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, formData);
  }

  deleteOffer(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
