package kr.co.polycube.backendtest.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.polycube.backendtest.exception.ApiException;
import kr.co.polycube.backendtest.users.domain.dto.UserDto;
import kr.co.polycube.backendtest.users.domain.dto.UserNameDto;
import kr.co.polycube.backendtest.users.domain.entity.User;
import kr.co.polycube.backendtest.users.service.UserService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void 유저_생성_테스트() throws Exception {
        //given
        UserDto userDto = new UserDto(1L, "테스트유저");

        //when
        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userDto.getId()));
    }

    @Test
    public void 유저_생성_중복_테스트() throws Exception {
        // given
//        UserDto userDto = new UserDto(1L, "테스트유저");
//        doThrow(new ApiException(HttpStatus.BAD_REQUEST, "ID 중복"))
//                .when(userService).createUser(userDto);
//
//        mvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(userDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.reason").value("ID 중복"));
    }

    @Test
    public void 유저_조회_테스트() throws Exception {
        //given
        User testUser = User.builder()
                .id(1L)
                .name("테스트유저")
                .build();

        given(userService.getUserById(testUser.getId())).willReturn(testUser);

        mvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.name").value(testUser.getName()));
    }

    @Test
    public void 유저_조회_404_테스트() throws Exception {
        //given
        given(userService.getUserById(2L)).willThrow(new ApiException(HttpStatus.NOT_FOUND, "유저 없음"));

        mvc.perform(get("/users/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason").value("유저 없음"));
    }

    @Test
    public void 유저_수정_테스트() throws Exception {
        //given
        Long id = 1L;
        String name = "수정테스트";
        UserNameDto userNameDto = new UserNameDto(name);

        doNothing().when(userService).updateUser(id, name);

        mvc.perform(MockMvcRequestBuilders.put("/users/1", userNameDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userNameDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(userNameDto.getName()));
    }

    @Test
    public void 유저_수정_빈_이름_테스트() throws Exception {
        //given
        UserNameDto userNameDto = new UserNameDto("");

        doThrow(new ApiException(HttpStatus.BAD_REQUEST, "유효하지 않은 이름"))
                .when(userService).updateUser(1L, userNameDto.getName());

        mvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userNameDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("유효하지 않은 이름"));
    }

    @Test
    public void 유저_수정_중복_테스트() throws Exception {
        //given
        UserNameDto userNameDto = new UserNameDto("수정테스트");

        doThrow(new ApiException(HttpStatus.BAD_REQUEST, "이미 등록된 이름"))
                .when(userService).updateUser(2L, userNameDto.getName());

        mvc.perform(MockMvcRequestBuilders.put("/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userNameDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("이미 등록된 이름"));
    }

    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
