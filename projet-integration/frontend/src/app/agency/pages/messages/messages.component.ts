import { Component, AfterViewInit, OnInit } from '@angular/core';
import { MessageService } from '../../../messages/message.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './messages.component.html',
})
export class MessagesComponent implements AfterViewInit, OnInit {
  receivedMessages: any[] = [];
  filteredMessages: any[] = []; // Only the last message from each user
  conversation: any[] = [];
  newMessageContent: string = '';

  agencyId = ''; // Example agency ID
  userId = 'T1'; // Example user ID (current user)

  constructor(private messageService: MessageService) {}

  ngAfterViewInit(): void {
    const button = document.getElementById('dropdownMenuIconButton');
    const menu = document.getElementById('dropdownDots');

    if (button && menu) {
      button.addEventListener('click', () => {
        menu.classList.toggle('hidden');
      });
    }
  }

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
    this.loadReceivedMessages(); // Load messages received by the agency
    this.loadConversation(); // Load conversation between the agency and the user

  }

  // Fetch messages received by the agency
  loadReceivedMessages() {
    this.messageService.getReceivedMessages(this.agencyId).subscribe(
      (response) => {
        this.receivedMessages = response;
        console.log('Agency received messages:', this.receivedMessages);

        // Filter messages to get only the latest one from each user
        this.filterLatestMessages();
      },
      (error) => {
        console.error('Error fetching received messages:', error);
      }
    );
  }

  // Filter messages to show only the last message from each user
  filterLatestMessages() {
    const messageMap = new Map<string, any>();

    // Iterate and keep only the latest message for each sender
    this.receivedMessages.forEach((message) => {
      messageMap.set(message.senderId, message);
    });

    // Convert the map to an array of messages
    this.filteredMessages = Array.from(messageMap.values());
    console.log('Filtered messages (latest from each user):', this.filteredMessages);

    // Sort by timestamp to show the most recent messages at the top
    this.filteredMessages.sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime());
  }

  // Fetch conversation between the agency and the user
  loadConversation() {
    this.messageService.getMessagesBetweenAgencyAndUser(this.agencyId, this.userId).subscribe(
      (response) => {
        this.conversation = response;
        console.log('Loaded conversation:', this.conversation);
        this.conversation.sort((a, b) => new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime());
      },
      (error) => {
        console.error('Error loading conversation:', error);
      }
    );
  }

  // Send a new message
  sendMessage(content: string) {
    if (!content.trim()) return; // Prevent sending empty messages

    const newMessage = {
      senderId: this.agencyId, // Change to `userId` if the user is sending
      receiverId: this.userId,
      content,
      timestamp: new Date().toISOString(),
    };

    this.messageService.sendMessage(newMessage).subscribe(
      (response) => {
        console.log('Message sent:', response);
        this.conversation.push(response); // Update conversation
        this.conversation.sort((a, b) => new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime());
        this.newMessageContent = ''; // Clear input field
      },
      (error) => {
        console.error('Error sending message:', error);
      }
    );
  }

  selectUser(senderId: string) {
    // Update the userId with the selected user's ID
    this.userId = senderId;
  
    // Reload the conversation for the selected user
    this.loadConversation();
  }
}
