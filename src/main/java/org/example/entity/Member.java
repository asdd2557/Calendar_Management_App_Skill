package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name ="member")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;



    public Member() {

    }

    public Member(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
