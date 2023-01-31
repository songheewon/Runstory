package com.runstory.api.controller;

import com.runstory.api.response.ApiResponse;
import com.runstory.api.response.FeedResDto;
import com.runstory.domain.feed.entity.Feed;
import com.runstory.service.FeedService;
import com.runstory.service.FollowService;
import com.runstory.service.RunningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Api(tags = "인덱스 페이지 API")
public class MainController {
    private final FeedService feedService;
    private final FollowService followService;
    private final RunningService runningService;
    @GetMapping("/running-location")
    @ApiOperation(value = "현재위치 기반 러닝 모임 조회", notes = "")
    public ResponseEntity<?> getRunningCrewByLocation (@RequestParam float latitude, @RequestParam float longitude, HttpServletRequest request){
        return null;
    }

    @GetMapping("/running-today")
    @ApiOperation(value = "오늘 마감 러닝 모임 조회", notes = "")
    public ResponseEntity<?> getRunningCrewByToday (HttpServletRequest request){
        return null;
    }

    @GetMapping("/feed")
    @ApiOperation(value = "나의 팔로잉 피드 사용자 조회", notes = "")
    public ApiResponse getFollowingFeedPages(@RequestParam Long lastFeedId, @RequestParam int size){
        System.out.println("lastFeedId: "+lastFeedId+" size: "+size);
        Page<Feed> feeds = feedService.findByFollowingFeedPages(lastFeedId, size, 1L);
        List<FeedResDto> result = feeds.stream().map(f -> new FeedResDto(f)).collect(Collectors.toList());
        return ApiResponse.success(result);
    }
}
