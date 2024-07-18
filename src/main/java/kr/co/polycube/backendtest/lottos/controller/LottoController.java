package kr.co.polycube.backendtest.lottos.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.polycube.backendtest.exception.ApiException;
import kr.co.polycube.backendtest.lottos.domain.dto.LottoResponseDto;
import kr.co.polycube.backendtest.lottos.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/lottos")
@RestController
public class LottoController {

    private final LottoService lottoService;

    @PostMapping("")
    public ResponseEntity create(HttpServletRequest request) throws ApiException {

        Long id = Long.valueOf(request.getHeader("id"));

        try {
            int[] numbers = lottoService.createLotto(id);
            LottoResponseDto lottoResponseDto = new LottoResponseDto(numbers);
            return ResponseEntity.status(HttpStatus.CREATED).body(lottoResponseDto);
        } catch (ApiException e) {
            throw e;
        }
    }
}
