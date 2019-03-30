package org.acms.WishlistService.model;

public class WishlistFullfillers {
	private int id;
	private String fullfiller_id;
	private int wishlist_id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullfiller_id() {
		return fullfiller_id;
	}
	public void setFullfiller_id(String fullfiller_id) {
		this.fullfiller_id = fullfiller_id;
	}
	public int getWishlist_id() {
		return wishlist_id;
	}
	public void setWishlist_id(int wishlist_id) {
		this.wishlist_id = wishlist_id;
	}
}
