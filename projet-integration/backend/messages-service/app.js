require('dotenv').config(); // Load environment variables from .env
const express = require('express');
const mongoose = require('mongoose');
const http = require('http');
const WebSocket = require('ws');
const bodyParser = require('body-parser');
const Message = require('./models/messages'); // Import the Message model

const app = express();
const server = http.createServer(app);
const wss = new WebSocket.Server({ server });
const cors = require('cors');

// Enable CORS for all origins
app.use(cors());

app.use(bodyParser.json());


mongoose
    .connect(process.env.MONGODB_URL, { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log('Connected to MongoDB'))
    .catch(err => console.error('MongoDB connection error:', err));

    wss.on('connection', (ws) => {
      console.log('New WebSocket connection established');
  
      ws.on('message', async (message) => {
          try {
              const parsedMessage = JSON.parse(message);
  
              const newMessage = new Message({
                  senderId: parsedMessage.senderId,
                  receiverId: parsedMessage.receiverId,
                  content: parsedMessage.content,
              });
  
              await newMessage.save();
  
              // Broadcast the message to all connected clients
              wss.clients.forEach((client) => {
                  if (client.readyState === WebSocket.OPEN) {
                      client.send(JSON.stringify(newMessage));
                  }
              });
          } catch (err) {
              console.error('Error processing message:', err);
          }
      });
  
      ws.on('close', () => {
          console.log('WebSocket connection closed');
      });
  });

app.get('/messages', async (req, res) => {
    try {
        const messages = await Message.find();
        res.status(200).json(messages);
    } catch (err) {
        res.status(500).json({ error: 'Failed to fetch messages' });
    }
});

// Create a new message
app.post('/messages', async (req, res) => {
  try {
      const { senderId, receiverId, content } = req.body;

      const newMessage = new Message({ senderId, receiverId, content });
      await newMessage.save();

      res.status(201).json(newMessage);
  } catch (err) {
      console.error('Error creating message:', err);
      res.status(500).json({ error: 'Failed to create message' });
  }
});



// Get all messages sent by a specific user
app.get('/messages/sent/:userId', async (req, res) => {
    try {
        const { userId } = req.params;
        console.log(userId);
        const messages = await Message.find({ senderId: userId}).sort({ timestamp: 1 });
  
        res.status(200).json(messages);
    } catch (err) {
        console.error('Error fetching sent messages:', err);
        res.status(500).json({ error: 'Failed to fetch sent messages' });
    }
  });
  
  // Get all messages received by a specific user
  app.get('/messages/received/:userId', async (req, res) => {
    try {
        const { userId } = req.params;
  
        const messages = await Message.find({ receiverId: userId }).sort({ timestamp: 1 });
  
        res.status(200).json(messages);
    } catch (err) {
        console.error('Error fetching received messages:', err);
        res.status(500).json({ error: 'Failed to fetch received messages' });
    }
  });
  


// Get all messages between two users
app.get('/messages/:user1/:user2', async (req, res) => {
  try {
      const { user1, user2 } = req.params;

      const messages = await Message.find({
          $or: [
              { senderId: user1, receiverId: user2 },
              { senderId: user2, receiverId: user1 },
          ],
      }).sort({ timestamp: 1 });

      res.status(200).json(messages);
  } catch (err) {
      console.error('Error fetching messages:', err);
      res.status(500).json({ error: 'Failed to fetch messages' });
  }
});
app.delete('/messages', async (req, res) => {
    try {
        await Message.deleteMany(); // Deletes all documents in the messages collection
        res.status(200).json({ message: 'All messages have been deleted' });
    } catch (err) {
        console.error('Error deleting messages:', err);
        res.status(500).json({ error: 'Failed to delete messages' });
    }
});

// Start the server
const PORT = process.env.PORT || 3001;
server.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
