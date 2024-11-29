import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private apiUrl = 'http://localhost:3002/api/notifications'; // Replace with your actual API URL

  constructor(private http: HttpClient) {}

  getNotificationsByReceiverId(receiverId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${receiverId}`);
  }
  
  deleteNotificationById(notificationId: string): Observable<any[]> {
    return this.http.delete<any[]>(`${this.apiUrl}/${notificationId}`);
  }
 
}
