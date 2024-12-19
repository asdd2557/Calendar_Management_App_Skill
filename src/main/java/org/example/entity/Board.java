package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;


    public Board(String title, String content, LocalDateTime createTime, LocalDateTime lastUpdated) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.lastUpdated = lastUpdated;
    }

    public Board() {

    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
