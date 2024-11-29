const mongoose = require('mongoose');

const MessageSchema = new mongoose.Schema({
    senderId: { type: String, required: true },       // Sender of the message
    receiverId: { type:String, required: true },    // Receiver of the message
    content: { type: String, required: true },     // Content of the message
    timestamp: { type: Date, default: Date.now },  // Timestamp of the message
});

module.exports = mongoose.model('Messages', MessageSchema);
