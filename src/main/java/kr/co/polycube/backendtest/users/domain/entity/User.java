package kr.co.polycube.backendtest.users.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
