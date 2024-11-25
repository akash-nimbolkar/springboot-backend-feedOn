package com.akash.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ContactInfo {

   private String email;
   private String mobile;
   private  String twitter;
   private  String instagram;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getTwitter() {
	return twitter;
}
public void setTwitter(String twitter) {
	this.twitter = twitter;
}
public String getInstagram() {
	return instagram;
}
public void setInstagram(String instagram) {
	this.instagram = instagram;
}
   
   
}
