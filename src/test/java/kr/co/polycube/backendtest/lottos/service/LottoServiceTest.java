package kr.co.polycube.backendtest.lottos.service;

import kr.co.polycube.backendtest.lottos.domain.entity.Lotto;
import kr.co.polycube.backendtest.lottos.domain.entity.Winner;
import kr.co.polycube.backendtest.lottos.domain.repository.LottoRepository;
import kr.co.polycube.backendtest.lottos.domain.repository.WinnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LottoServiceTest {

    @Mock   //실제 데이터베이스 쓰지 않음
    private LottoRepository lottoRepository;

    @Mock
    private WinnerRepository winnerRepository;

    @Spy
    @InjectMocks
    private LottoService lottoService;

    @Test
    public void 로또_당첨_발표() {
        //given
        int[] winNumbers = {1, 2, 3, 4, 5, 6};

        Lotto lotto1 = Lotto.builder()
                .id(1L)
                .numbers(new int[]{1, 2, 3, 4, 5, 6})
                .build();

        Lotto lotto2 = Lotto.builder()
                .id(2L)
                .numbers(new int[]{1, 2, 3, 4, 5, 20})
                .build();

        Lotto lotto3 = Lotto.builder()
                .id(3L)
                .numbers(new int[]{11, 12, 13, 14, 15, 16})
                .build();

        List<Lotto> lottoList = new ArrayList<>();
        lottoList.add(lotto1);
        lottoList.add(lotto2);
        lottoList.add(lotto3);

        given(lottoRepository.findAll()).willReturn(lottoList);

        //protected 메서드 행위 설정
        doReturn(winNumbers).when(lottoService).generateLotto();

        //then
        lottoService.pickLottoWinners();

        ArgumentCaptor<Winner> captor = ArgumentCaptor.forClass(Winner.class);
        //save가 2번 되었는가?
        verify(winnerRepository, times(2)).save(captor.capture());

        //winnerRepository.findAll() == 2?
        List<Winner> winnerList = captor.getAllValues();
        assertThat(winnerList).hasSize(2);
        //winnerRepository.findById(1L).getRank() == 1?
        assertThat(winnerList.get(0).getRank()).isEqualTo(1);
        //winnerRepository.findById(2L).getRank() == 2?
        assertThat(winnerList.get(1).getRank()).isEqualTo(2);
    }
}
