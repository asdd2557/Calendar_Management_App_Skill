package org.example.repository;


import org.example.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    default Member findMemberByUsernameOrElseThrow(String username) {
        return findMemberByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    default Member findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @Query("SELECT m.id FROM Member m WHERE m.email = :email AND m.password = :password")
    Optional<Long> findIdByEmailAndPassword(@Param("email") String username, @Param("password") String password);


    Optional<Member> findByEmailAndPassword(String email, String password);
}
