import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private apiUrl = 'http://localhost:3001/messages'; // Backend API URL

  constructor(private http: HttpClient) {}

  // Get messages received by a specific user or agency
  getReceivedMessages(userIdOrAgencyId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/received/${userIdOrAgencyId}`);
  }

  // Get conversation between an agency and a user
  getMessagesBetweenAgencyAndUser(agencyId: string, userId: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${agencyId}/${userId}`);
  }

  // Send a new message
  sendMessage(message: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, message);
  }
}
