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
import com.spring.minipos.entity.Category;
import com.spring.minipos.entity.MessageModel;
import com.spring.minipos.service.CategoryServices;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
	
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	@Autowired
	CategoryServices categoryServices;
	
	@Autowired
	UserServices userServices;
	
	@PostMapping("create/category")
	public String createCategory(@Valid @RequestBody Category category,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(categoryServices.findAll()
							.stream().filter(x->category.getCategoryName()
									.equals(x.getCategoryName())
									).findAny().orElse(null) != null) {
						result = new Gson().toJson(new MessageModel("Please change category name."));
					}else {
						result = new Gson().toJson(categoryServices.save(category));	
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
	
	@GetMapping("categories")
	public String showAllCategories(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result =  gson.toJson(categoryServices.findAll()); 
				}else {
					result = new Gson().toJson(new MessageModel("No permission."));
				}
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@PostMapping("update/category")
	public String updateCatgegory(@Valid @RequestBody Category category,
			@RequestParam(value = "authKey", required = false) String authKey) throws NoSuchAlgorithmException {
		String result = null;
		Category categoryByUsername = categoryServices.findCategoryByName(category.getCategoryName());
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if (categoryServices.findCategoryById(category.getId()) != null) {
						if (categoryByUsername!= null && categoryByUsername.getId() != category.getId()) {
							result = gson.toJson(new MessageModel("Please change category name."));
						} else {
							result = gson.toJson(categoryServices.save(category));
						}
					} else {
						result = gson.toJson(new MessageModel("no category detail."));
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
	
	@GetMapping("category/{id}")
	public String getCategoryById(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if(categoryServices.findCategoryById(id) !=null) {
					result = gson.toJson(categoryServices.findCategoryById(id));
				}else {
					result = gson.toJson(new MessageModel("no category detail."));
				}
			}else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	
	@DeleteMapping("/delete/category/{id}")
	public String removeCategory(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(categoryServices.findCategoryById(id) !=null) {
						if(categoryServices.findCategoryById(id).getProducts().size()>0) {
							result = gson.toJson(new MessageModel("This category still have products."));
						}else {
							Category category = new Category();
							category = categoryServices.findCategoryById(id);
							categoryServices.delete(category);
							result = gson.toJson(new MessageModel("Success."));
						}
					}else {
						result = gson.toJson(new MessageModel("no category detail."));
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
	
	
	//Mobile controllers
	@GetMapping("categories/mobile")
	public String showAllCategoriesMobile(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
					result =  gson.toJson(categoryServices.findAll()); 
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
}
