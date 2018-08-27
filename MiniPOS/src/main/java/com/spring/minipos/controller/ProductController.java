package com.spring.minipos.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring.minipos.entity.Message;
import com.spring.minipos.entity.Product;
import com.spring.minipos.service.ProductServices;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
	
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	@Autowired
	UserServices userServices;
	
	@Autowired
	ProductServices productServices;
	
	@PostMapping("create/product")
	public String createProduct(@Valid @RequestBody Product product,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(productServices.findAll()
							.stream().filter(x->product.getProductName()
									.equals(x.getProductName())
									).findAny().orElse(null) != null) {
						result = new Gson().toJson(new Message("Please change product name."));
					}else {
						result = new Gson().toJson(productServices.save(product));	
					}
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("products")
	public String showAllProducts(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(productServices.findAll());
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
	@PostMapping("update/product")
	public String updateProduct(@Valid @RequestBody Product product,
			@RequestParam(value = "authKey", required = false) String authKey) throws NoSuchAlgorithmException {
		String result = null;
		Product productByProductBarcodeID = productServices.findProductByProductBarcodeID(product.getProductBarcodeID());
		Product productByProductName = productServices.findProductByProductName(product.getProductName());
		if (authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if (productServices.findProductById(product.getId()) != null) {
						if (productByProductName!= null && productByProductName.getId() != product.getId()) {
							result = gson.toJson(new Message("Please change product name."));
						}else if(productByProductBarcodeID!=null && productByProductBarcodeID.getId() != product.getId()) {
							result = gson.toJson(new Message("Please change product barcode."));
						}else {
							result = gson.toJson(productServices.save(product));
						}
					} else {
						result = gson.toJson(new Message("no product detail."));
					}
				} else {
					result = gson.toJson(new Message("No permission."));
				}
			} else {
				result = gson.toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
	@DeleteMapping("delete/product/{id}")
	public String removeProduct(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(productServices.findProductById(id)!=null) {
						Product product = new Product();
						product = productServices.findProductById(id);
						productServices.delete(product);
					}else {
						result = gson.toJson(new Message("No user found."));
					}
				} else {
					result = gson.toJson(new Message("No permission."));
				}
			} else {
				result = gson.toJson(new Message("Wrong auth key."));
			}
		}

		return result;
	}
	
}
