package com.runstory.service;

import com.runstory.api.request.RunningCrewReqDto;
import com.runstory.api.response.RunningMainResDto;
import com.runstory.domain.hashtag.HashtagType;
import com.runstory.domain.hashtag.dto.HashtagDto;
import com.runstory.domain.hashtag.dto.SelectedHashtagDto;
import com.runstory.domain.hashtag.entity.Hashtag;
import com.runstory.domain.hashtag.entity.SelectedHashtag;
import com.runstory.domain.running.Running;
import com.runstory.domain.running.RunningDetail;
import com.runstory.api.response.RunningDetailSumDto;
import com.runstory.domain.running.dto.RunningDto;
import com.runstory.domain.user.entity.User;
import com.runstory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RunningServiceImpl implements RunningService {

    private final RunningRepository runningrepository;
    private final RunningDetailRepository runningDetailRepository;
    private final HashtagRepository hashtagRepository;
    private final SelectedHashtagRepository selectedHashtagRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long createRunningCrew(RunningCrewReqDto runningCrewReqDto, Long userSeq){// User user 현재 유저를 들고와야한다.
        // 유저 전체의 데이터를 들고온다.
        User user = userRepository.findByUserSeq(userSeq);
        Running running = new Running(runningCrewReqDto, user);
        runningrepository.save(running);
        System.out.println(1);
        RunningDetail runningDetail = new RunningDetail(runningCrewReqDto);
        runningDetailRepository.save(runningDetail);
        // RunningHashTag
        for (Long hashtagId : runningCrewReqDto.getHastag()){
            // HashTag 관련 Repository 필요
            Hashtag hashtag = hashtagRepository.findHashtagByHashtagId(hashtagId);
            SelectedHashtag selectedHashtag = SelectedHashtag.builder()
                    .hashtagType(HashtagType.RUNNING)
                    .running(running)
                    .hashtag(hashtag)
                    .build();
            selectedHashtagRepository.save(selectedHashtag);
        }
        return running.getRunningId();
    }


//    @Override
//    public ArrayList<HashMap<String, ArrayList<RunningMainResDto>>> selectRunningCrew(float longitude, float latitude){
//        List<Running> runninglist = runningrepository.findByIsFinished(false); // 데이터 전체를 들고온다.
//        HashMap<String, List<RunningMainResDto>> hash = new HashMap<>();
//        List<HashMap<String, List<RunningMainResDto>>> result = new ArrayList<>();
//        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul")); // 현재 서울의 시간을 보여준다.
//        for (Running running: runninglist){
//            int year = running.getStartTime().getYear();
//            int day = running.getStartTime().getDayOfYear();
//            if (Math.abs(running.getStartLatitude() - latitude) < 0.01 && Math.abs(running.getStartLongitude() - longitude) < 0.01){
//                RunningMainResDto runningMainResDto = new RunningMainResDto(running);
//                result.add(runningMainResDto);
//            }else if (day == seoulNow.getDayOfYear() && year == seoulNow.getYear()){
//                RunningMainResDto runningMainResDto = new RunningMainResDto(running);
//                result.add(runningMainResDto);
//            }
//
//            User user = userRepository.findByUserSeq(1L); // 유저가 없는 경우를 추가해서 넣어준다.
//            System.out.println(user);
//            List<SelectedHashtag> hashtags = selectedHashtagRepository.findAllByUser(user);
//            System.out.println(hashtags);
//            for (SelectedHashtag hashtag : hashtags){ // 유저 각각의 해시태그들을 가져온다.
//            }


            // User의 HashTag를 받아와서 확인하는 방법이 필요하므로 도연님하고 확인 후에 진행해야 한다.
            // HashTag_id에 따라 HashTagTable을 들고와서 List를 확인
            // User의 HashTag를 먼저 확인(해당 사용자의 HashTag)
//            for (SelectedHashtag selectedHashtag : running.getSelectedHashtags()){
//                if (selectedHashtag.getRunning() != null){
//
//                }
//            }
//        }
//        return result;
//    }
    
    // DetailPage 들고오기
    @Override
    public RunningDetailSumDto findRunningDetail(Long id){
        Running running = runningrepository.getById(id);
        RunningDetail runningDetail = runningDetailRepository.getById(id);
        RunningDetailSumDto runningDetailSumDto = new RunningDetailSumDto(running, runningDetail);
        return runningDetailSumDto;
    }

    // DetailPage 삭제하기
    @Override
    @Transactional
    public Long deleteRunningCrew(Long id){
        Running running = runningrepository.getById(id);
        RunningDetail runningDetail = runningDetailRepository.getById(id);
        runningrepository.deleteById(id);
        runningDetailRepository.deleteById(id);
        return id;
    }

    // DetailPage 수정하기
    @Override
    @Transactional
    public Long updateRunningCrew(RunningCrewReqDto newRunningCrewReqDto){
        Running running = runningrepository.getById(newRunningCrewReqDto.getId()); // 값읋 들고온다.
        RunningDetail runningDetail = runningDetailRepository.getById(newRunningCrewReqDto.getId());
        running.builder()
                .crewName(newRunningCrewReqDto.getCrewName())
                .distance(newRunningCrewReqDto.getDistance())
                .endLatitude(newRunningCrewReqDto.getEndLatitude())
                .endLocation(newRunningCrewReqDto.getEndLocation())
                .endLongitude(newRunningCrewReqDto.getEndLongitude())
                .endTime(newRunningCrewReqDto.getEndTime())
                .imgFileName(newRunningCrewReqDto.getImgFileName())
                .imgFilePath(newRunningCrewReqDto.getImgPathFile())
                .runningContent(newRunningCrewReqDto.getRunningContent())
                .startLatitude(newRunningCrewReqDto.getStartLatitude())
                .startLocation(newRunningCrewReqDto.getStartLocation())
                .startLongitude(newRunningCrewReqDto.getStartLongitude())
                .startTime(newRunningCrewReqDto.getStartTime())
                .build();
        runningrepository.save(running);
        runningDetail.builder()
                .total(newRunningCrewReqDto.getTotal())
                .man(newRunningCrewReqDto.getMan())
                .women(newRunningCrewReqDto.getWomen())
                .minAge(newRunningCrewReqDto.getMinAge())
                .maxAge(newRunningCrewReqDto.getMaxAge())
                .hasDog(newRunningCrewReqDto.isHasDog())
                .build();
        runningDetailRepository.save(runningDetail);

        // 해쉬태그를 변경
        List<SelectedHashtag> selectedHashtags = selectedHashtagRepository.findAllByRunning(running);
        for (SelectedHashtag selectedHashtag : selectedHashtags){

            selectedHashtagRepository.deleteById(selectedHashtag.getSelectedHashtagId());
        }

        for (Long hashtagId : newRunningCrewReqDto.getHastag()){
            Hashtag hashtag = hashtagRepository.findHashtagByHashtagId(hashtagId);
            SelectedHashtag selectedHashtag = SelectedHashtag.builder()
                    .hashtagType(HashtagType.RUNNING)
                    .running(running)
                    .hashtag(hashtag)
                    .build();
            selectedHashtagRepository.save(selectedHashtag);
        }


//        for (SelectedHashtag selectedHashtag : selectedHashtags){
//            boolean isHash = false;
//            for (Long hashtagId : newRunningCrewReqDto.getHastag()){
//                if (selectedHashtag.getSelectedHashtagId().equals(hashtagId)){
//                    isHash = true;
//                    break;
//                }
//            }
//            if (!isHash){
//                selectedHashtagRepository.deleteById(selectedHashtag.getSelectedHashtagId());
//            }
//        }

        return newRunningCrewReqDto.getId();
    }

    // DetailPage 삭제하기

}
