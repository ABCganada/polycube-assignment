package kr.co.polycube.backendtest.lottos.service;

import kr.co.polycube.backendtest.exception.ApiException;
import kr.co.polycube.backendtest.lottos.domain.entity.Lotto;
import kr.co.polycube.backendtest.lottos.domain.repository.LottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class LottoService {

    private final LottoRepository lottoRepository;

    public int[] createLotto(Long id) throws ApiException {

        if (lottoRepository.existsById(id)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "해당 유저 로또 존재");
        }

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

        Lotto lottos = Lotto.builder()
                .id(id)
                .numbers(numbers)
                .build();

        lottoRepository.save(lottos);

        return numbers;
    }
}
