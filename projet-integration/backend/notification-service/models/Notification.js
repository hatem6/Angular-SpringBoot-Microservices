const mongoose = require('mongoose');

const NotificationSchema = new mongoose.Schema({
    notificationId: { type: String, required: true },       
    receiverId: { type:String, required: true },   
    message: { type: String, required: true },   
});

module.exports = mongoose.model('Notifications', NotificationSchema);
