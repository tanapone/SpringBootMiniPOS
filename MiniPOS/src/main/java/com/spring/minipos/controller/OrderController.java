package com.spring.minipos.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
					result = new Gson().toJson(new MessageModel("Required auth key."));
				}else{
					if(userServices.checkAuthKey(authKey)!=null){
						Order newOrder = new Order((userServices.checkAuthKey(authKey)));
						newOrder.setOrderDetails(order.getOrderDetails());
	
						for(OrderDetail orderDetails: newOrder.getOrderDetails()) {
							orderDetails.setOrder(newOrder);
							//UpdateStock
							Product product = new Product();
							product = productServices.findProductById(orderDetails.getProduct().getId());
							int oldQty = product.getProductQty();
							int newQty = oldQty - orderDetails.getProductAmount();
							product.setProductQty(newQty);
							orderDetails.setProduct(product);
							orderDetails.setProductCaptialPrice(product.getProductCapitalPrice());
							orderDetails.setProductSalePrice(product.getProductSalePrice());
							newOrder.setUser(userServices.checkAuthKey(authKey));
							productServices.save(orderDetails.getProduct());
							
						}	

							orderServices.save(newOrder);
							result = gson.toJson(new MessageModel("Success"));
						
					}else{
						result = new Gson().toJson(new MessageModel("Wrong auth key."));
					}
				}
				
				return result;
			}
	
	
	@GetMapping("/orders")
	public String showAllOrders(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				result = gson.toJson(orderServices.findAll());
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("/order/last")
	public String getLastOrder(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				result = gson.toJson(orderServices.getLast());
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	

	@GetMapping("/order/{startDate}/{endDate}")
	public String getOrderBetweenDate(@PathVariable String startDate, @PathVariable String endDate,
			@RequestParam(value="authKey" ,required=false) String authKey) throws ParseException {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date startDayDate = df.parse(startDate);
				Date endDayDate = df.parse(endDate);
				Date realEndDayDate = addDays(endDayDate,1);
				result = gson.toJson(orderServices.getOrderBetweenDate(startDayDate, realEndDayDate));
			}else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}
	
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
    
	@GetMapping("/order/{date}")
	public String getOrderBetweenDate(@PathVariable String date) throws ParseException {
		String result = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(date);
		Date endDayDate = addDays(startDate,1);
		result = gson.toJson(orderServices.findOrderByDate(startDate,endDayDate));
		return result;
	}
	
	@GetMapping("/order/quater/{quater}/{year}")
	public String getOrderByQuater(@PathVariable int quater,@PathVariable String year,
			@RequestParam(value="authKey" ,required=false) String authKey) throws ParseException {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				String fromDate = null;
				String toDate = null;
				
					if(quater==1) {
						fromDate = year+"-01-01";
						toDate = year+"-03-31";
					}else if(quater==2) {
						fromDate = year+"-04-01";
						toDate = year+"-06-31";
					}else if(quater==3) {
						fromDate = year+"-07-01";
						toDate = year+"-09-30";
					}else if(quater==4) {
						fromDate = year+"-10-01";
						toDate = year+"-12-31";
					}
					
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date startDayDate = df.parse(fromDate);
					Date endDayDate = df.parse(toDate);
					Date realEndDayDate = addDays(endDayDate,1);
					result = gson.toJson(orderServices.getOrderBetweenDate(startDayDate, realEndDayDate));
					
				}else {
					result = new Gson().toJson(new MessageModel("Wrong auth key."));
				}
			}
		return result;
		
	}
	

}
