package org.acms.WishlistService.model;

public class Catalog {
	
	private int product_id;
	private String product_name;
	private String brand;
	private String description;
	private float price;
	private int quantity;
	
	private String pic_location;
	
	public int getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(int id) {
		this.product_id = id;
	}
	
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String name) {
		this.product_name = name;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getPic_location() {
		return pic_location;
	}

	public void setPic_location(String pic_location) {
		this.pic_location = pic_location;
	}
}
