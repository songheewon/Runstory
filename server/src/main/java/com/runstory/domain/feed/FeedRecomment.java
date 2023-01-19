package com.runstory.domain.feed;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FeedRecomment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedRecommnetId;
    private long feedCommentId;
    @Column(length = 50)
    private String userID;
    @Column(length = 500)
    private String cotent;
    @Column(columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime regdate;
}
