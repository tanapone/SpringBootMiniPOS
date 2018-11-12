package com.spring.minipos.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import com.spring.minipos.entity.MD5;
import com.spring.minipos.entity.MessageModel;
import com.spring.minipos.entity.User;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	@Autowired
	UserServices userServices;

	@PostMapping("/create/user")
	public String createUserAcc(@Valid @RequestBody User user,
			@RequestParam(value = "authKey", required = false) String authKey) throws NoSuchAlgorithmException {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if (userServices.findUserByUsername(user.getUsername()) != null) {
						result = gson.toJson(new MessageModel("Please change username."));
					} else if (userServices.findUserByEmail(user.getEmail()) != null) {
						result = gson.toJson(new MessageModel("Please change email."));
					} else {
						String encodePassword = new MD5(user.getPassword()).Encoding();
						user.setPassword(encodePassword);
						userServices.save(user);
						result = gson.toJson(new MessageModel("Success."));
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

	@PostMapping("/update/user")
	public String updateUserAcc(@Valid @RequestBody User user,
			@RequestParam(value = "authKey", required = false) String authKey) throws NoSuchAlgorithmException {
		String result = null;
		User userByUsername = userServices.findUserByUsername(user.getUsername());
		User userByEmail = userServices.findUserByEmail(user.getEmail());
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if (userServices.findUserByUsername(user.getUsername()) != null) {
						if (userByUsername!= null && !userByUsername.getAuthKey().equalsIgnoreCase(user.getAuthKey())) {
							System.out.println("user in = "+user.getAuthKey() + "\n Query auth = "+userByUsername.getAuthKey());
							
							result = gson.toJson(new MessageModel("Please change username."));
						} else if (userByEmail != null && !userByEmail.getAuthKey().equalsIgnoreCase(user.getAuthKey())) {
							result = gson.toJson(new MessageModel("Please change email."));
						} else {
							userServices.save(user);
							result = gson.toJson(new MessageModel("Success."));
						}
					} else {
						result = gson.toJson(new MessageModel("no user detail."));
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
	
	@DeleteMapping("/delete/user/{username}")
	public String removeUser(@PathVariable String username
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(userServices.findUserByUsername(username)!=null) {
						if(userServices.findUserByUsername(username).getOrders().size()>0) {
							result = gson.toJson(new MessageModel("User is in order please change user status."));
						}else {
							User user = new User();
							user = userServices.findUserByUsername(username);
							userServices.delete(user);
							result = gson.toJson(new MessageModel("Success."));
							
						}
					}else {
						result = gson.toJson(new MessageModel("No user found."));
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

	@GetMapping("/users")
	public String showAllUsers(@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(userServices.findAll());
				} else {
					result = gson.toJson(new MessageModel("No permission."));
				}
			} else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}

		return result;
	}

	@GetMapping("/user/username/{username}")
	public String findUserByUsername(@PathVariable String username,
			@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;

		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(userServices.findUserByUsername(username));
				} else {
					result = gson.toJson(new MessageModel("No permission."));
				}
			} else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;

	}

	@GetMapping("/user/email/{email}")
	public String findUserByEmail(@PathVariable String email,
			@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;

		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(userServices.findUserByEmail(email));
				} else {
					result = gson.toJson(new MessageModel("No permission."));
				}
			} else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;

	}

	@PostMapping("/login/admin")
	public String checkLoginAdmin(@RequestBody User user) throws NoSuchAlgorithmException {
		if (userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding()) != null) {
			if (userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding())
					.getUserType() != 1) {
				return gson.toJson(new MessageModel("No permission."));
			} else {
				if(userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding()).isUserStatus()==true) {
					return gson.toJson(userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding()));
				}else {
					return gson.toJson(new MessageModel("Your account was closed."));
				}
			}
		} else {
			return gson.toJson(new MessageModel("Wrong username or password."));
		}
	}

	@GetMapping("/user")
	public String getUserByAuthKey(@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				result = gson.toJson(userServices.checkAuthKey(authKey));
			} else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}

	@GetMapping("/create/user/admin")
	public String createAdminUser() throws ParseException, NoSuchAlgorithmException {
		if (userServices.findAll().size() < 1) {
			User user = new User();
			user.setUsername("admin");
			user.setPassword(new MD5("123456").Encoding());
			user.setFirstName("Tanapone");
			user.setLastName("Kanongsri");
			user.setPhoneNumber("0931385440");
			user.setUserType(1);
			user.setEmail("tanapone58110@gmail.com");
			user.setAddress("298/23");
			user.setUserStatus(true);
			return gson.toJson(userServices.save(user));
		} else {
			return gson.toJson(new MessageModel("Admin account already set."));
		}

	}

//	@GetMapping("/user/last")
//	public String getLastUser(@RequestParam(value = "authKey", required = false) String authKey) {
//		String result = null;
//		if (authKey == null) {
//			result = new Gson().toJson(new MessageModel("Required auth key."));
//		} else {
//			if (userServices.checkAuthKey(authKey) != null) {
//				result = gson.toJson(userServices.getLastUser());
//			} else {
//				result = gson.toJson(new MessageModel("Wrong auth key."));
//			}
//		}
//		return result;
//	}
	
	@PostMapping("/user/resetPassword")
	public String resetUserPassword(@RequestBody User user) throws NoSuchAlgorithmException {
		String result = null;
				if(userServices.checkAuthKey(user.getAuthKey())!=null) {
					String encodingPassword = new MD5(user.getPassword()).Encoding();
					user.setPassword(encodingPassword);
					userServices.save(user);
					result = gson.toJson(new MessageModel("Success."));
				}else {
					result = gson.toJson(new MessageModel("No user found."));
				}
		return result;
	}
	
	//Mobile Controller
	@PostMapping("/login/mobile")
	public String checkLogin(@RequestBody User user) throws NoSuchAlgorithmException {
		if (userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding()) != null) {
			if(userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding()).isUserStatus()==true) {
				return gson.toJson(userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding()));
			}else {
				return gson.toJson(new MessageModel("Your account was closed."));
			}
		} else {
			return gson.toJson(new MessageModel("Wrong username or password."));
		}
	}

	@PostMapping("/user/sentResetPassword")
	public String sentResetPasswordUrlToMail(@RequestBody User user) {
		String result = null;
			if(user.getEmail() == null) {
				result = new Gson().toJson(new MessageModel("Required email."));
			}else {
				if(userServices.findUserByEmail(user.getEmail())!=null) {
					try{
			            String host ="smtp.gmail.com" ;
			            String userMail = "tanapone58110@gmail.com";
			            String pass = "zvaynveqfenzhvtz";
			            String to = userServices.findUserByEmail(user.getEmail()).getEmail();
			            String from = "tanapone58110@gmail.com";
			            String subject = "ลิงค์สำหรับแก้ไขรหัสผ่าน - MiniPOS";
			            String messageText = "ขอบคุณ คุณ "+userServices.findUserByEmail(user.getEmail()).getFirstName()
			            		+" ที่ใช้บริการ MiniPOS นี่คือลิงค์สำหรับเปลี่ยนรหัสผ่านใหม่ "
			            		+ ": http://localhost:4200/reset-user-password?authKey="+userServices.findUserByEmail(user.getEmail()).getAuthKey();
			            boolean sessionDebug = false;

			            Properties props = System.getProperties();

			            props.put("mail.smtp.starttls.enable", "true");
			            props.put("mail.smtp.host", host);
			            props.put("mail.smtp.port", "587");
			            props.put("mail.smtp.auth", "true");
			            props.put("mail.smtp.starttls.required", "true");

			            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			            Session mailSession = Session.getDefaultInstance(props, null);
			            mailSession.setDebug(sessionDebug);
			            Message msg = new MimeMessage(mailSession);
			            msg.setFrom(new InternetAddress(from));
			            InternetAddress[] address = {new InternetAddress(to)};
			            msg.setRecipients(Message.RecipientType.TO, address);
			            msg.setSubject(subject); msg.setSentDate(new Date());
			            msg.setText(messageText);

			           Transport transport=mailSession.getTransport("smtp");
			           transport.connect(host, userMail, pass);
			           transport.sendMessage(msg, msg.getAllRecipients());
			           transport.close();
			     
			           result = gson.toJson(new MessageModel("Success."));
			        }catch(Exception ex)
			        {
			            System.out.println(ex);
			        }
				}else {
					result = new Gson().toJson(new MessageModel("Wrong email."));
				}
			}
		return result;
	}
	
}
