package member;

import java.sql.Timestamp;

public class MemberBean {
   private String id;
   private String pass;
   private String pass2;
   private String re_pass;
   private String name;  
   private String email;
   private String email2;
   private Timestamp reg_date;
   private String address;
   private String address2;
   private String phone;
   private String mobile;
   
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
public String getPass2() {
	return pass2;
}
public void setPass2(String pass2) {
	this.pass2 = pass2;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getEmail2() {
	return email2;
}
public void setEmail2(String email2) {
	this.email2 = email2;
}
public Timestamp getReg_date() {
	return reg_date;
}
public void setReg_date(Timestamp reg_date) {
	this.reg_date = reg_date;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getAddress2() {
	return address2;
}
public void setAddress2(String address2) {
	this.address2 = address2;
}
public String getRe_pass() {
	return re_pass;
}
public void setRe_pass(String re_pass) {
	this.re_pass = re_pass;
}

   
}

