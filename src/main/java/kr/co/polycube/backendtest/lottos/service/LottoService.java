package kr.co.polycube.backendtest.lottos.service;

import kr.co.polycube.backendtest.exception.ApiException;
import kr.co.polycube.backendtest.lottos.domain.entity.Lotto;
import kr.co.polycube.backendtest.lottos.domain.entity.Winner;
import kr.co.polycube.backendtest.lottos.domain.repository.LottoRepository;
import kr.co.polycube.backendtest.lottos.domain.repository.WinnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class LottoService {

    private final LottoRepository lottoRepository;
    private final WinnerRepository winnerRepository;
    private boolean checkMode = false;

    public int[] createLotto(Long id) throws ApiException {

        if (checkMode) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "점검 시간");
        }

        if (lottoRepository.existsById(id)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "해당 유저 로또 존재");
        }

        int[] numbers = generateLotto();

        Lotto lottos = Lotto.builder()
                .id(id)
                .numbers(numbers)
                .build();

        lottoRepository.save(lottos);

        return numbers;
    }

    public void pickLottoWinners() {
        //당첨 번호 생성
        int[] winNumbers = generateLotto();
        for (int i = 0; i < winNumbers.length; i++) {
            System.out.print(" " + winNumbers[i]);
        }
        //당첨 선정
        List<Lotto> lottoList = lottoRepository.findAll();

        for (Lotto lotto : lottoList) {
            int[] cur = lotto.getNumbers();
            int match = 0, j = 0;

            for (int i = 0; i < cur.length; i++) {
                if (j == winNumbers.length) {
                    break;
                }

                if (cur[i] == winNumbers[j]) {
                    match++;
                    j++;
                } else if (cur[i] > winNumbers[j]) {
                    i--;
                    j++;
                }
            }

            //2개 이상 맞추면 저장
            if (match >= 2) {
                Winner winner = Winner.builder()
                        .lotto(lotto)
                        .rank(7 - match)
                        .build();
                winnerRepository.save(winner);
            }
        }
    }

    public void cleanLottoDatas() {
        lottoRepository.deleteAll();
    }

    public void setCheckMode(boolean checkMode) {
        this.checkMode = checkMode;
    }

    protected int[] generateLotto() {
        int[] numbers = new int[6];
        Set<Integer> set = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < numbers.length; i++) {
            int num = random.nextInt(45) + 1;
            if (set.contains(num)) {
                i--;
                continue;
            }

            numbers[i] = num;
            set.add(num);
        }

        Arrays.sort(numbers);
        return numbers;
    }
}
