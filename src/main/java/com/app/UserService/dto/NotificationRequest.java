package com.app.UserService.dto;

public class NotificationRequest {
	
	 private NotificationType notificationType;
	    private ChannelType channelType;

	    private String userId;
	    private String email;
	    
	    
		public NotificationRequest() {
			super();
		}
		public NotificationRequest(NotificationType notificationType, ChannelType channelType, String userId,
				String email) {
			super();
			this.notificationType = notificationType;
			this.channelType = channelType;
			this.userId = userId;
			this.email = email;
		}
		public NotificationType getNotificationType() {
			return notificationType;
		}
		public void setNotificationType(NotificationType notificationType) {
			this.notificationType = notificationType;
		}
		public ChannelType getChannelType() {
			return channelType;
		}
		public void setChannelType(ChannelType channelType) {
			this.channelType = channelType;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	    
	    

}
