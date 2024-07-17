package kr.co.polycube.backendtest.users.controller;

import jakarta.validation.Valid;
import kr.co.polycube.backendtest.users.domain.dto.UserIdDto;
import kr.co.polycube.backendtest.users.domain.dto.UserNameDto;
import kr.co.polycube.backendtest.users.domain.dto.UserDto;
import kr.co.polycube.backendtest.users.domain.entity.User;
import kr.co.polycube.backendtest.exception.ApiException;
import kr.co.polycube.backendtest.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    //user 등록 API
    @PostMapping("")
    public ResponseEntity join(@Valid @RequestBody UserDto userDto) throws ApiException {

        try {
            userService.createUser(userDto);
            UserIdDto response = new UserIdDto(userDto.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ApiException e) {
            throw e;
        }
    }

    //user 조회 API
    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable("id") Long id) throws ApiException {

        try {
            User user = userService.getUserById(id);
            UserDto userDto = new UserDto(user.getId(), user.getName());
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (ApiException e) {
            throw e;
        }
    }

    //user 수정 API
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody UserNameDto userNameDto) throws ApiException {

        try {
            userService.updateUser(id, userNameDto.getName());
            UserDto userDto = new UserDto(id, userNameDto.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (ApiException e) {
            throw e;
        }
    }
}
