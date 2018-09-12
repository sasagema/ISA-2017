package com.example.isa2017.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa2017.converters.UserToUserDTO;
import com.example.isa2017.model.Friendship;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.FriendshipDTO;
import com.example.isa2017.modelDTO.UserDTO;
import com.example.isa2017.service.FriendshipService;
import com.example.isa2017.service.UserService;

@RestController
@RequestMapping(value = "/friendship")
public class FriendshipController {
	
	@Autowired
	private FriendshipService friendshipService;
	@Autowired
	private UserToUserDTO toUserDTO;
	@Autowired
	private UserService userService;
	
	@RequestMapping( value = "/addFriend", method= RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Friendship> addFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendDTO){
		//treba mi ulogovani user
		System.out.println("Usli u FRIENDSHIP ADDFRIEND");
		User logged = (User) request.getSession().getAttribute("logged");
		if(logged!=null){
			friendshipService.addFriend(logged,friendDTO);
			//userService.getAllUsers(logged);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/displayFriendRequests", method= RequestMethod.GET)
	public ResponseEntity<List<UserDTO>>  displayRequests(HttpServletRequest request){
		//System.out.println("Usli u displayFriendRequests");
		User logged = (User) request.getSession().getAttribute("logged");
		List<User> friends=friendshipService.getFriendshipRequests(logged);
		
		//treba da vratimo listu requestova
		return new ResponseEntity<>(toUserDTO.convert(friends),HttpStatus.OK);
	}
	
	//myfriends
	@RequestMapping(value="/displayFriendAccepted", method= RequestMethod.GET)
	public ResponseEntity<List<UserDTO>>  displayRequestsAccepted(HttpServletRequest request){
		//System.out.println("Usli u displayFriendRequests");
		User logged = (User) request.getSession().getAttribute("logged");
		List<User> friends=friendshipService.getFriendshipAccepted(logged);
		
		//treba da vratimo listu requestova
		return new ResponseEntity<>(toUserDTO.convert(friends),HttpStatus.OK);
	}
	
	
	
	@RequestMapping( value = "/accept", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity acceptFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
		System.out.println("Usli u ACCEPT KONTROLEEEEER");
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		System.out.println("Prijatelj je"+friendshipDTO.getId());
		
		friendshipService.acceptFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping( value = "/reject", method= RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity rejectFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
		System.out.println("Usli u Reject kontroler");
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		System.out.println("Prijatelj je"+friendshipDTO.getId());
		
		friendshipService.rejectFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	@RequestMapping( value = "/delete", method= RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteFriend(HttpServletRequest request,@RequestBody FriendshipDTO friendshipDTO){
	
		User logged = (User) request.getSession().getAttribute("logged");
		System.out.println("ULogovan je"+logged.getId());
		System.out.println("Prijatelj je"+friendshipDTO.getId());
		
		friendshipService.deleteFriend(logged,friendshipDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
}
