package kr.co.polycube.backendtest.lottos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Table(name = "lotto")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lotto {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @ElementCollection
    private int[] numbers;

    @OneToOne(mappedBy = "lotto", cascade = CascadeType.ALL)
    private Winner winner;
}
