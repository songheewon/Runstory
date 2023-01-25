package com.runstory.domain.user.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@DynamicInsert
public class User_Block {
    @Id @GeneratedValue
    private Long blockId;
    @Comment("차단하는 사용자 아이디")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name="fk_user_block_user_id_user_user_id"))
    private User user;

    @Comment("차단당한 사용자 아이디")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="blocked_user_id", foreignKey = @ForeignKey(name="fk_user_blocK_blocked_user_id_user_user_id"))
    private User blocked;
}