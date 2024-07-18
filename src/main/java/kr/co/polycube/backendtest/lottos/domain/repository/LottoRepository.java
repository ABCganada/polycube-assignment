package kr.co.polycube.backendtest.lottos.domain.repository;

import kr.co.polycube.backendtest.lottos.domain.entity.Lotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoRepository extends JpaRepository<Lotto, Long> {
}
