package kr.co.polycube.backendtest.lottos.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LottoDto {

    private Long id;
    private int[] numbers;
}
