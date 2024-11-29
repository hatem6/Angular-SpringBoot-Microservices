const Notification = require("../models/Notification");

const createNotification = async (req, res, next) => {
    try {
      // Find the highest notificationId in the collection
      const lastNotification = await Notification.findOne().sort({ notificationId: -1 });
  
      // Determine the next notificationId (start from 1 if no notifications exist)
      const nextNotificationId = lastNotification ? parseInt(lastNotification.notificationId) + 1 : 1;
  
      // Create the new notification with the incremented notificationId
      const newNotification = new Notification({
        ...req.body,
        notificationId: nextNotificationId,
      });
  
      // Save the new notification
      await newNotification.save();
  
      res.status(201).json(newNotification);
    } catch (error) {
      next(error); // Pass errors to the error handling middleware
    }
  };

const getNotificationsByReceiverId = async (req, res, next) => {
    try {
      const { receiverId } = req.params; // Extract receiverId from route parameters
      const notifications = await Notification.find({ receiverId });
      if (notifications.length === 0) {
        return res.status(404).json({ message: "No notifications found for this receiverId" });
      }
      res.json(notifications);
    } catch (error) {
      next(error);
    }
  };

const deleteNotificationById = async (req, res, next) => {
    try {
      const { notificationId } = req.params; // Extract notificationId from URL params
  
      // Find and delete the notification
      const deletedNotification = await Notification.findOneAndDelete({ notificationId });
  
      if (!deletedNotification) {
        return res.status(404).json({ message: "Notification not found" });
      }
  
      res.status(200).json({ message: "Notification deleted successfully", deletedNotification });
    } catch (error) {
      next(error); // Pass errors to the error handling middleware
    }
  };

module.exports = {
  createNotification,
  getNotificationsByReceiverId,
  deleteNotificationById,
};
