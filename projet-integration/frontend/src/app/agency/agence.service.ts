import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Agence } from './agence.interface'; 

@Injectable({
  providedIn: 'root',
})
export class AgenceService {
  private apiUrl = 'http://localhost:8888/api/agences'; // Use your API Gateway URL

  constructor(private http: HttpClient) {}

  getAgences(): Observable<Agence[]> {
    return this.http.get<Agence[]>(this.apiUrl);
  }
  
  loginAgence(email: string, password: string): Observable<any> {
    const loginPayload = { email, password };
    
    // Post the email and password to the login endpoint
    return this.http.post<any>(`${this.apiUrl}/login`, loginPayload);
  }

  signUp(formData: FormData): Observable<any> {
    // Append '/create' to the API URL to match the endpoint in your backend
    return this.http.post<any>(`${this.apiUrl}/create`, formData);
  }

  // Other methods like createAgence, updateAgence, etc.
}
