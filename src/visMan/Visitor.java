package visMan;

import javafx.scene.control.ToggleGroup;
import visMan.utils.Utils;

public class Visitor {
	final String SEP=" - ";
	private String uID;
	private String name, gender,contact,dateOfBirth,address,category,purpose;
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
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
		this.gender = gender.substring(0,1).toUpperCase();
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
		this.category = category.substring(0,1).toUpperCase();
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
	public Visitor(String uID, String name, String gender, String contact, String dateOfBirth, String address, String category,
			String purpose) {
		setuID(uID);
		setName(name);
		setGender(gender);
		setContact(contact);
		setDateOfBirth(purpose);
		setAddress(address);
		setCategory(category);
		setPurpose(purpose);
	}
	public Visitor(String uID, String name, ToggleGroup gender, String contact, String dateOfBirth, String address, ToggleGroup category,
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
		this.uID=null;
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
		this.uID=null;
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
}
