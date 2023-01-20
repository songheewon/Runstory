package com.runstory.api.controller.running;

import com.runstory.api.Response.running.RunningListDTO;
import com.runstory.api.service.RunningService;
import com.runstory.domain.running.Running;
import com.runstory.api.repository.running.RunningRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/running")
public class RunningController {
    @Autowired
    private RunningService runningservice;

    @GetMapping("/main/{runningid}")
    public ResponseEntity<?> view(@PathVariable Long runningid) throws Exception{
        Map<String, Object> result = new HashMap<>();

        try {
            result.put("statuscode", "200");
            result.put("message", "해당 게시글은 있을 예정입니다.");
            result.put("success", "true");
            result.put("data",runningservice.findrunningInfo(runningid));
            return ResponseEntity.ok().body(result);
        }catch(Exception E) {
            return  ResponseEntity.status(500).body("해당 게시글은 없습니다.");
        }
    }
    }
//    public RunningListDTO runninginfo() {
//        return runningservice.findrunningInfo(1L);
//    }
//}
