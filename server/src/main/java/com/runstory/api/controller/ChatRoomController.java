package com.runstory.api.controller;

import com.runstory.api.response.BaseResponse;
import com.runstory.common.auth.CustomUserDetails;
import com.runstory.domain.chat.ChatRoomUser;
import com.runstory.domain.chat.dto.ChatRoomDto;
import com.runstory.domain.user.entity.User;
import com.runstory.repository.ChatRoomRepository;
import com.runstory.repository.ChatRoomUserRepository;
import com.runstory.repository.UserRepository;
import com.runstory.service.ChatService;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {

    // ChatRepository Bean 가져오기
    @Autowired
    private ChatService chatService;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    ChatRoomUserRepository chatRoomUserRepository;
    @Autowired
    UserRepository userRepository;

    // 채팅 리스트 화면
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
    @GetMapping("")
    public BaseResponse<?> goChatRoom(){

        List<ChatRoomDto> list = chatService.findAllRoom();
        log.info("SHOW ALL " + " {}",list);
            return BaseResponse.success(list);
    }

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/createroom")
    public BaseResponse<?> createRoom(@ApiIgnore Authentication authentication, @RequestParam Long userId) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Long myId = userDetails.getUserSeq();

        String roomName = "";
        if(myId <= userId){
            roomName += (myId+"-"+userId);
        }else {
            roomName += (userId+"-"+myId);
        }

        // 이미 만든 채팅방이 있는지 확인
        User my = userRepository.findByUserSeq(myId);
        User user = userRepository.findByUserSeq(userId);
        HashSet<Long> set = new HashSet<>();
        List<ChatRoomUser> myChattingList = chatRoomUserRepository.findByUser(my);
        for (ChatRoomUser room : myChattingList){
            set.add(room.getChatRoomUserId());
        }
        myChattingList = chatRoomUserRepository.findByUser(user);
        for (ChatRoomUser room : myChattingList){
            if(set.contains(room.getChatRoomUserId())){
                // 이미 채팅방이 존재하는 경우
                return BaseResponse.customSuccess(404,"이미 존재하는 방입니다.",room.getChatRoomUserId());
            }
        }



        ChatRoomDto room = chatService.createChatRoom(myId,userId);
        log.info("CREATE Chat Room {}", room);
        return BaseResponse.success(roomName);
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/room")
    public BaseResponse<?> roomDetail(String roomId){

        log.info("roomId {}", roomId);
        ChatRoomDto room =  chatService.findRoomById(roomId);
            return BaseResponse.success(room);
    }

}