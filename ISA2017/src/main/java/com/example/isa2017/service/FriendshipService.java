package com.example.isa2017.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.isa2017.model.Friendship;
import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.FriendshipDTO;
import com.example.isa2017.modelDTO.UserDTO;



@Service
public interface FriendshipService {

	Friendship save(Friendship friendship);

	void addFriend(User logged, FriendshipDTO friendDTO);

	List<User> getFriendshipRequests(User logged);

	void acceptFriend(User logged, FriendshipDTO friendshipDTO);

	Friendship getFriendship(User logged, FriendshipDTO friendshipDTO, String status);

	boolean isAdded(User logged, FriendshipDTO friendDTO, String string);
}
