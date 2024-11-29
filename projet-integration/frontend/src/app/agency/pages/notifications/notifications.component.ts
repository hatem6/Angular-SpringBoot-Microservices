import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../../../notifications/notification.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notifications.component.html',
})
export class NotificationsComponent implements OnInit {
  receivedNotifications: any[] = [];
  agencyId: string = '';

  constructor(private notificationService: NotificationService) {}
  ngOnInit() {
    const agence = localStorage.getItem('agence');

    // If agence exists in localStorage, parse it and extract the name
    if (agence) {
      try {
        const agenceData = JSON.parse(agence);
        if (agenceData && agenceData.name) {
          this.agencyId = 'A'+agenceData.id;
          console.log(this.agencyId);
        } else {
          console.error('Agency name is missing in localStorage data');
        }
      } catch (error) {
        console.error('Error parsing agence data from localStorage', error);
      }
    } else {
      console.log('No agence found in localStorage');
    }
    this.loadReceivedNotification();
  }

  loadReceivedNotification() {
    this.notificationService.getNotificationsByReceiverId(this.agencyId).subscribe(
      (response) => {
        this.receivedNotifications = response;
        console.log('Agency received notifications:', this.receivedNotifications);
      },
      (error) => {
        console.error('Error fetching received notifications:', error);
      }
    );
  }

  deleteNotification(notificationId: string) {
    this.notificationService.deleteNotificationById(notificationId).subscribe(
      () => {
        // Remove the notification from the local array
        this.receivedNotifications = this.receivedNotifications.filter(
          (notification) => notification.notificationId !== notificationId
        );
        console.log(`Notification ${notificationId} deleted successfully.`);
      },
      (error) => {
        console.error('Error deleting notification:', error);
      }
    );
  }

}
