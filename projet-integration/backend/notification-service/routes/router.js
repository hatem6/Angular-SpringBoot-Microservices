const express = require("express");
const { createNotification, getNotificationsByReceiverId,deleteNotificationById } = require("../controllers/notificationController");

const router = express.Router();

router.post("/", createNotification);

router.get("/:receiverId", getNotificationsByReceiverId);

router.delete("/:notificationId", deleteNotificationById);

module.exports = router;
