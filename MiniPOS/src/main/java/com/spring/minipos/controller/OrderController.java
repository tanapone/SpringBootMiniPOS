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
	
	@PostMapping("/create/order")
	public String createOrder(@Valid @RequestBody List<OrderDetail> orderDetail,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
					Order order = new Order((userServices.checkAuthKey(authKey)));
						for(OrderDetail orderDetails : orderDetail) {
							orderDetails.setOrder(order);
							order.getOrderDetails().add(orderDetails);
							double sumPrice = orderDetails.getProduct().getProductSalePrice() * orderDetails.getProductAmount();
							double profit = sumPrice - (orderDetails.getProduct().getProductCapitalPrice() * orderDetails.getProductAmount()); 
							order.setSumPrice(sumPrice);
							order.setProfit(profit);
							productServices.save(orderDetails.getProduct());
							orderServices.save(order);
						}
						result = gson.toJson(order);
			}else {
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
}
