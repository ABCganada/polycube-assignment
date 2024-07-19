package kr.co.polycube.backendtest.lottos;

import kr.co.polycube.backendtest.exception.ApiException;
import kr.co.polycube.backendtest.lottos.controller.LottoController;
import kr.co.polycube.backendtest.lottos.domain.dto.LottoDto;
import kr.co.polycube.backendtest.lottos.service.LottoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@AutoConfigureMockMvc
@WebMvcTest(LottoController.class)
public class LottoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LottoService lottoService;

    @InjectMocks
    private LottoController lottoController;


    @Test
    public void 로또_생성() throws Exception {
        //given
        int[] numbers = {1, 2, 3, 4, 5, 6};
        LottoDto lottoDto = new LottoDto(1L, numbers);

        given(lottoService.createLotto(1L)).willReturn(lottoDto.getNumbers());

        mvc.perform(MockMvcRequestBuilders.post("/lottos")
                .header("id", lottoDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers.length()").value(lottoDto.getNumbers().length))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers[0]").value(numbers[0]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers[1]").value(numbers[1]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers[2]").value(numbers[2]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers[3]").value(numbers[3]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers[4]").value(numbers[4]))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers[5]").value(numbers[5]));
    }

    @Test
    public void 로또_생성_점검_시간() throws Exception {
        //given
        Long id = 1L;

        given(lottoService.createLotto(1L)).willThrow(new ApiException(HttpStatus.BAD_REQUEST, "점검 시간"));

        mvc.perform(MockMvcRequestBuilders.post("/lottos")
                .header("id", id.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reason").value("점검 시간"));
    }

    @Test
    public void 로또_생성_중복() throws Exception {
        //given
        Long id = 1L;

        given(lottoService.createLotto(id)).willThrow(new ApiException(HttpStatus.BAD_REQUEST, "해당 유저 로또 존재"));

        mvc.perform(MockMvcRequestBuilders.post("/lottos")
                        .header("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reason").value("해당 유저 로또 존재"));
    }
}
