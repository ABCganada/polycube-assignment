package kr.co.polycube.backendtest.users.service;

import kr.co.polycube.backendtest.users.domain.dto.UserDto;
import kr.co.polycube.backendtest.users.domain.entity.User;
import kr.co.polycube.backendtest.users.domain.repository.UserRepository;
import kr.co.polycube.backendtest.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //유저 생성 로직
    public void createUser(UserDto userDto) throws ApiException {

        if (userRepository.existsById(userDto.getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "ID 중복");
        }

        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .build();

        userRepository.save(user);
    }

    //유저 조회 로직
    public User getUserById(Long id) throws ApiException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "유저 없음"));
    }

    //유저 수정 로직
    @Transactional
    public void updateUser(Long id, String name) throws ApiException {

        User user = getUserById(id);

        if (name == null || name.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "유효하지 않은 이름");
        }

        if (user.getName().equals(name)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "이미 등록된 이름");
        }

        user.setName(name);
        userRepository.save(user);
    }
}
