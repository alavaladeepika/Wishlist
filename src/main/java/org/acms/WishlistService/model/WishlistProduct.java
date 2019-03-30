package org.acms.WishlistService.model;

public class WishlistProduct {
	
	private int id;
	private int product_id;
	private int wishlist_id;
	private int quantity;
	private String address;
	private String reason;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getWishlist_id() {
		return wishlist_id;
	}
	public void setWishlist_id(int wishlist_id) {
		this.wishlist_id = wishlist_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
