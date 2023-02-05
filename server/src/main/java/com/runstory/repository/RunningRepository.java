package com.runstory.repository;

import com.runstory.domain.running.Running;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface RunningRepository extends JpaRepository<Running, Long> {
    ArrayList<Running> findByIsFinished(Boolean isfinished);
    Running getById(Long id);
    @Query(value = "select *, (6371*acos(cos(radians( :latitude))*cos(radians(start_latitude))*cos(radians(start_longitude)" +
            " -radians(:longitude))+sin(radians(:latitude))*sin(radians(start_latitude)))) as distance " +
            "from Running where is_finished = 0 having distance <= 1 order by distance", nativeQuery = true)
    List<Running> findByLocation(@Param("latitude") float latitude, @Param("longitude") float longitude);

    @Query("select r from Running r where r.isFinished = false and date(r.startTime) = date(now())")
    List<Running> findByStartTime();
}
