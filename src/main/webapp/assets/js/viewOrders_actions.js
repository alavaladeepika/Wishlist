/**
 * Manisha
 */
jQuery(document)
		.ready(
				function($) {

					$("#header").load("header.html");

					// Get the url parameters
					$.urlParam = function(name) {
						var results = new RegExp('[\?&]' + name + '=([^&#]*)')
								.exec(window.location.href);
						if (results == null) {
							return null;
						}
						return decodeURI(results[1]) || 0;
					}

					var wishlist_id = $.urlParam("wishlistid");
					var login_id = checkCookie("login_id");

					if (login_id == null) {
						window.location = "login.html";
					}

					// Initial page setting
					var url = "http://localhost:8080/WishlistService/webapi/creator/getOrders/"
							+ wishlist_id;
					$
							.ajax({
								type : 'POST',
								contentType : 'application/json',
								url : url,
								success : function(items) {

									console.log(items);
									if (!$.isEmptyObject(items)) {
										var prod_no;

										for (var i = 0; i < items.length; i++) {
											prod_no = items[i].id;

											var product = "<div id='prod_details_"
													+ prod_no
													+ "' class='col-lg-12' style='border: 2px solid #eee'>"
													+ "<div class='col-lg-4' style='padding: 5px'>"
													+ "<img id='prod_img_"
													+ prod_no
													+ "' src='#' alt='Card image' style='margin-top:30px;margin-left:25px;width:200px;height:200px;float:left;'/>"
													+ "</div>"
													+

													"<div>"
													+ "<div class='form-group' style='margin-left:320px'>"
													+ "<div><h4 id='name_"
													+ prod_no
													+ "' class='card-title'>Pen</h4></div>"
													+

													"<div><label id='price_"
													+ prod_no
													+ "' class='col-form-label' style='font-weight: bold; color: #800000; font-size: 20px;'>Rs.100</label></div>"
													+ "<div><h3 id='purchasedby_"
													+ prod_no
													+ "' class='col-form-label' style=' font-size: 20px;'>Manisha</h3></div>"
													+

													"<div><label id='quantity_"
													+ prod_no
													+ "' class='col-form-label' style='color: green; font-size: 15px;'>3</label></div>"
													+ "<div><label id='brand_"
													+ prod_no
													+ "' class='col-form-label' style='color: gray; font-size: 14px;'>Brand: </label></div>"
													+ "<div><label id='description_"
													+ prod_no
													+ "' class='col-form-label' style='color: gray; font-size: 14px;'>Description: </label></div>"
													+

													"<br>"
													+ "</div>"
													+ "</div>" + "</div>";
											// console.log(items_list[i]);
											$("#prod_details").append(product);
											$('#prod_img_' + prod_no).attr(
													"src",
													items[i].pic_location);
											$("#name_" + prod_no).text(
													items[i].product_name);
											$("#brand_" + prod_no).text(
													"Brand: " + items[i].brand);
											$("#description_" + prod_no)
													.text(
															"Description: "
																	+ items[i].description);
											$("#price_" + prod_no).text(
													"Rs. " + items[i].price);
											$("#quantity_" + prod_no).text(
													"Purchased Quantity: "
															+ items[i].quantity
															+ " unit(s)");
											$("#purchasedby_" + prod_no)
													.text(
															"Fullfiller's Name: "
																	+ items[i].fullfiller_name);

											$("#prod_details_" + prod_no)
													.show();

										}
									} else {

										var no_items = "<div id='no_items' align='center' style='border: 2px solid #eee;'>"
												+ "<label class='col-form-label'>No orders placed for this wishlist yet.</label>"
												+ "</div>";
										$("#prod_details").append(no_items);
										$("#no_items").show();
									}
								},
								error : function(items) {
									alert("failed");
								}
							});
				});