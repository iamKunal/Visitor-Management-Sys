package visMan;

import javafx.scene.control.ToggleGroup;
import visMan.utils.Utils;

public class Visitor {
	final String SEP=" - ";
	private int uID;
	private String name, gender,contact,dateOfBirth,address,category,purpose;
	public int getuID() {
		return uID;
	}
	public void setuID(int uID) {
		this.uID = uID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name.trim();
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		try{
		this.gender = gender.substring(0,1).toUpperCase();
		}
		catch (Exception e) {
			this.gender="M";
		}
	}
	public void setGender(ToggleGroup gender){
		setGender(Utils.getToggleText(gender));
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address.trim();
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		try{
		this.category = category.substring(0,1).toUpperCase();
		}
		catch (Exception e) {
			this.category="O";
		}
	}
	public void setCategory(ToggleGroup category){
		setCategory(Utils.getToggleText(category));
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose.trim();
	}
	public Visitor(){
		this.uID=0;
		this.name=this.gender=this.contact=this.dateOfBirth=this.address=this.category=this.purpose="";
	}
	public Visitor(int uID, String name, String gender, String contact, String dateOfBirth, String address, String category,
			String purpose) {
		setuID(uID);
		setName(name);
		setGender(gender);
		setContact(contact);
		setDateOfBirth(dateOfBirth);
		setAddress(address);
		setCategory(category);
		setPurpose(purpose);
	}
	public Visitor(int uID, String name, ToggleGroup gender, String contact, String dateOfBirth, String address, ToggleGroup category,
			String purpose) {
		setuID(uID);
		setName(name);
		setGender(gender);
		setContact(contact);
		setDateOfBirth(dateOfBirth);
		setAddress(address);
		setCategory(category);
		setPurpose(purpose);
	}
	public Visitor(String name, String gender, String contact, String dateOfBirth, String address, String category,
			String purpose) {
		this.uID=0;
		setName(name);
		setGender(gender);
		setContact(contact);
		setDateOfBirth(dateOfBirth);
		setAddress(address);
		setCategory(category);
		setPurpose(purpose);
	}
	public Visitor(String name, ToggleGroup gender, String contact, String dateOfBirth, String address, ToggleGroup category,
			String purpose) {
		this.uID=0;
		setName(name);
		setGender(gender);
		setContact(contact);
		setDateOfBirth(dateOfBirth);
		setAddress(address);
		setCategory(category);
		setPurpose(purpose);
	}
	@Override
	public String toString() {
		return name + SEP + gender + SEP + contact + SEP + dateOfBirth + SEP + address + SEP + category + SEP + purpose;
	}
	public boolean equals(Visitor visitor){
		   if (this==visitor) return true;
		   if (this == null) return false;
		   if (this.getClass() != visitor.getClass()) return false;
		   boolean same=true;
		   same&=(this.uID==visitor.getuID())
				   & (this.name.equals(visitor.getName()))
				   & (this.gender.equals(visitor.getGender()))
				   & (this.contact.equals(visitor.getDateOfBirth()))
				   & (this.dateOfBirth.equals(visitor.getDateOfBirth()))
				   & (this.address.equals(visitor.getAddress()));
		   return same;
	}
}
