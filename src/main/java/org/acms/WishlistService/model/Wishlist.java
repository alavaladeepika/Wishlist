package org.acms.WishlistService.model;

public class Wishlist {
	private int wishlist_id;
	private String name;
	private String creator_id;
	private String status;
	
	public int getWishlist_id() {
		return wishlist_id;
	}
	public void setWishlist_id(int wishlist_id) {
		this.wishlist_id = wishlist_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
