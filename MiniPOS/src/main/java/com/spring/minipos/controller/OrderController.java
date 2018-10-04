package com.spring.minipos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring.minipos.entity.Message;
import com.spring.minipos.entity.Order;
import com.spring.minipos.entity.OrderDetail;
import com.spring.minipos.entity.Product;
import com.spring.minipos.service.OrderServices;
import com.spring.minipos.service.ProductServices;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
	
	@Autowired
	OrderServices orderServices;
	
	@Autowired
	ProductServices productServices;
	
	@Autowired
	UserServices userServices;
	
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	@PostMapping("create/order")
	public String createOrder(@Valid @RequestBody Order order,
			@RequestParam(value="authKey", required=false) String authKey){
				String result = null;
				
				if(authKey == null){
					result = new Gson().toJson(new Message("Required auth key."));
				}else{
					if(userServices.checkAuthKey(authKey)!=null){
						Order newOrder = new Order((userServices.checkAuthKey(authKey)));
						newOrder.setOrderDetails(order.getOrderDetails());
						double sumPrice = 0;
						double profit = 0;
						for(OrderDetail orderDetails: newOrder.getOrderDetails()) {
							orderDetails.setOrder(newOrder);
							//Calculate 
							double newSumPrice = orderDetails.getProduct().getProductSalePrice() * orderDetails.getProductAmount();
							double newProfit = newSumPrice - (orderDetails.getProduct().getProductCapitalPrice() * orderDetails.getProductAmount()); 
							sumPrice+=newSumPrice;
							profit+=newProfit;
							//UpdateStock
							Product product = new Product();
							product = productServices.findProductById(orderDetails.getProduct().getId());
							int oldQty = product.getProductQty();
							int newQty = oldQty - orderDetails.getProductAmount();
							product.setProductQty(newQty);
							orderDetails.setProduct(product);
							newOrder.setUser(userServices.checkAuthKey(authKey));
							productServices.save(orderDetails.getProduct());
							
						}	
							newOrder.setSumPrice(sumPrice);
							newOrder.setProfit(profit);
							orderServices.save(newOrder);
							result = gson.toJson(new Message("Success"));
						
					}else{
						result = new Gson().toJson(new Message("Wrong auth key."));
					}
				}
				
				return result;
			}
	
	
	@GetMapping("/orders")
	public String showAllOrders(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				result = gson.toJson(orderServices.findAll());
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("/order/last")
	public String getLastOrder(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				result = gson.toJson(orderServices.getLast());
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	

}
