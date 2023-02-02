package com.runstory.api.controller;

import com.runstory.api.request.RunningCrewReqDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.runstory.api.response.BaseResponse;
import com.runstory.api.response.RunningDetailSumDto;
import com.runstory.api.response.RunningMainResDto;
import com.runstory.common.auth.CustomUserDetails;
import com.runstory.domain.running.Running;
import com.runstory.domain.user.entity.Follow;
import com.runstory.service.RunningService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/running")
public class RunningController {
    @Autowired
    private RunningService runningservice;


    @PostMapping("") // RunningCrew 생성
    @ApiOperation(value = "Running Crew Create")
    public BaseResponse<?> createRunningCrew(@ApiIgnore Authentication authentication,@RequestBody RunningCrewReqDto runningCrewReqDto, HttpServletRequest request){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Long userSeq = userDetails.getUserSeq(); // 로그인된 유저 Seq를 들고 온다.
        Long result =  runningservice.createRunningCrew(runningCrewReqDto, userSeq);
        return BaseResponse.success(result);
    }

//    @GetMapping("") // RunningCrew Read
//    @ApiOperation(value = "Running Crew Read")
//    public BaseResponse<?> getRunnninCrew(@RequestParam("latitude") float latitude, @RequestParam("longitude") float longtitude){
//        ArrayList<HashMap<String, ArrayList<RunningMainResDto>>> runningMainResDtos = runningservice.selectRunningCrew(latitude, longtitude);
//        return BaseResponse.success(runningMainResDtos);
//    }

    //    @DeleteMapping("/detail/{runningid}")
//    public ResponseEntity<?> runningdelete(@PathVariable Long runningid) throws  Exception{
//
//    }


    /*
    * 여기서부터는 상세페이지에 관한 내용
    * */
    @GetMapping("/detail/{runningid}") // 상세페이지를 Read
    public BaseResponse<?> runningdetail(@PathVariable Long runningid, HttpServletRequest request){
        RunningDetailSumDto runningDetailSumDto =  runningservice.findRunningDetail(runningid);
        return BaseResponse.success(runningDetailSumDto);
    }

    @DeleteMapping("/detail/{runningid}") // 상세페이지 삭제하기
    @ApiOperation(value = "상세페이지 삭제")
    public BaseResponse<?> runningCrewDelete(@ApiIgnore Authentication authentication, @PathVariable Long runningid, HttpServletRequest request){
        Long id = runningservice.deleteRunningCrew(runningid);
        return BaseResponse.success(id);
    }

    @PutMapping("/detail/{runningid}") // 상세페이지 수정
    public BaseResponse<?> runningCrewUpdate(@ApiIgnore Authentication authentication, @PathVariable Long runningid, @RequestBody RunningCrewReqDto newRunningCrewReqDto, HttpServletRequest request){
        Long id = runningservice.updateRunningCrew(newRunningCrewReqDto);
        return BaseResponse.success(newRunningCrewReqDto);
    }

    // 찜 누를 때 기능
    /*
    * 여기는 댓글 관련한 기능입니다.
    * */
    // 댓글 생성, 댓글 삭제 기능

    /*
    * 개인 피드에 관한 자료입니다.
    * */

}