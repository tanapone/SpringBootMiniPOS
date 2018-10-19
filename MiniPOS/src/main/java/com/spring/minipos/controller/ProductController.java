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
import com.spring.minipos.entity.MessageModel;
import com.spring.minipos.entity.Product;
import com.spring.minipos.service.OrderServices;
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
	
	@Autowired
	OrderServices orderServices;
	
	@PostMapping("create/product")
	public String createProduct(@Valid @RequestBody Product product,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(productServices.findProductByProductName(product.getProductName())!=null) {
						result = new Gson().toJson(new MessageModel("Please change product name."));
					}else {
						if(productServices.findProductByProductBarcodeID(product.getProductBarcodeID())!=null) {
							result = gson.toJson(new MessageModel("Please change product barcode."));
						}else {
							result = new Gson().toJson(productServices.save(product));	
						}
					}
				}else {
					result = new Gson().toJson(new MessageModel("No permission."));
				}
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("products")
	public String showAllProducts(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(productServices.findAll());
				}else {
					result = new Gson().toJson(new MessageModel("No permission."));
				}
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
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
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if (productServices.findProductById(product.getId()) != null) {
						if (productByProductName!= null && productByProductName.getId() != product.getId()) {
							result = gson.toJson(new MessageModel(""
									+ ""));
						}else if(productByProductBarcodeID!=null && productByProductBarcodeID.getId() != product.getId()) {
							result = gson.toJson(new MessageModel("Please change product barcode."));
						}else {
							result = gson.toJson(productServices.save(product));
						}
					} else {
						result = gson.toJson(new MessageModel("no product detail."));
					}
				} else {
					result = gson.toJson(new MessageModel("No permission."));
				}
			} else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("product/{id}")
	public String findProductById(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(productServices.findProductById(id)!=null) {
						result = gson.toJson(productServices.findProductById(id));
					}else {
						result = gson.toJson(new MessageModel("No product found."));
					}
				}else{
					result = gson.toJson(new MessageModel("No permission."));	
				}
			}else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@DeleteMapping("delete/product/{id}")
	public String removeProduct(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(productServices.findProductById(id)!=null) {
						if(productServices.findProductById(id).getOrderDetails().size()<1) {
							if(productServices.findProductById(id).getInvoiceDetail().size()<1) {
								Product product = new Product();
								product = productServices.findProductById(id);
								productServices.delete(product);
								result = gson.toJson(new MessageModel("Success."));
							}else{
								result = gson.toJson(new MessageModel("Product is in invoice please change product status."));
							}
						}else {
							result = gson.toJson(new MessageModel("Product is in order please change product status."));
						}
					}else{
						result = gson.toJson(new MessageModel("No product found."));
					}
				}else{
					result = gson.toJson(new MessageModel("No permission."));
				}
			}else{
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}

		return result;
	}
	
	@GetMapping("products/less")
	public String showAllLessProducts(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(productServices.findLessProducts());
				}else {
					result = new Gson().toJson(new MessageModel("No permission."));
				}
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	// Mobile controller
	@GetMapping("products/mobile")
	public String showAllProductsMobile(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
					result = gson.toJson(productServices.findAll());
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("products/mobile/less")
	public String showAllLessProductsMobile(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
					result = gson.toJson(productServices.findLessProductsMobile());
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("product/mobile/searchName")
	public String findAllProductLikeName(@RequestParam(value="authKey" ,required=false) String authKey,@RequestParam(value="name" ,required=true) String searchName) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				result = gson.toJson(productServices.findProductByName(searchName));
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
	return result;
	}
	
	@GetMapping("product/mobile/{id}")
	public String findProductByIdMobile(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
					if(productServices.findProductById(id)!=null) {
						result = gson.toJson(productServices.findProductById(id));
					}else {
						result = gson.toJson(new MessageModel("No product found."));
					}
			}else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("product/mobile/categories")
	public String findProductByCategories(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
					if(productServices.findProductById(id)!=null) {
						result = gson.toJson(productServices.findProductByCategory(id));
					}else {
						result = gson.toJson(new MessageModel("No product found."));
					}
			}else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("product/mobile/category/{id}")
	public String findProductByCategory(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
					if(productServices.findProductById(id)!=null) {
						result = gson.toJson(productServices.findProductByCategory(id));
					}else {
						result = gson.toJson(new MessageModel("No product found."));
					}
			}else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	
	@GetMapping("product/mobile/searchBarcode")
	public String findProductByBarcode(@RequestParam(value="authKey" ,required=false) String authKey,
			@RequestParam(value="barcodeID" ,required=true) String barcodeID) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				result = gson.toJson(productServices.findProductByProductBarcodeID(barcodeID));
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
	return result;
	}
}
