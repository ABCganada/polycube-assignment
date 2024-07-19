package kr.co.polycube.backendtest.lottos.config;

import kr.co.polycube.backendtest.lottos.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LottoBatchConfig {

    private final LottoService lottoService;

    @Scheduled(cron = "0 0 0 ? * SUN")
    public void pickBatch() {
        lottoService.setCheckMode(true);
        lottoService.pickLottoWinners();
    }

    @Scheduled(cron = "0 0 12 ? * SUN")
    public void cleanBatch() {
        lottoService.cleanLottoDatas();
        lottoService.setCheckMode(false);
    }
}
