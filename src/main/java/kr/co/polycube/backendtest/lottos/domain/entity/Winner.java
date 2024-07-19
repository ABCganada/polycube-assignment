package kr.co.polycube.backendtest.lottos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Table(name = "winner")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Winner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotto_id", unique = true)
    private Lotto lotto;

    @Column(nullable = false)
    private int rank;
}
