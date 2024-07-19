package kr.co.polycube.backendtest.lottos.domain.repository;

import kr.co.polycube.backendtest.lottos.domain.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepository extends JpaRepository<Winner, Long> {
}
