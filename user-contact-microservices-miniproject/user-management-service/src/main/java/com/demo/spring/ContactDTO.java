package com.demo.spring;

public class ContactDTO {

		private int contactId;
		private String contactTag;
		private String city;
		private String pincode;
		private String email;
		private int userId;
		

		public ContactDTO() {
			
		}
		
		public ContactDTO(int contactId, String contactTag, String city, String pincode, String email, int userId) {
			this.contactId = contactId;
			this.contactTag = contactTag;
			this.city = city;
			this.pincode = pincode;
			this.email = email;
			this.userId = userId;
		}


		public int getContactId() {
			return contactId;
		}

		public void setContactId(int contactId) {
			this.contactId = contactId;
		}

		public String getContactTag() {
			return contactTag;
		}

		public void setContactTag(String contactTag) {
			this.contactTag = contactTag;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPincode() {
			return pincode;
		}

		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}
		
}
