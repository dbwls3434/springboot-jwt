package org.yujin.myproc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yujin.myproc.dto.UserDTO;
import org.yujin.myproc.entity.User;
import org.yujin.myproc.repository.UserRepository;
import org.yujin.myproc.util.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expireTimeMs = 1000 * 60 * 60; //1 hour

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) throws Exception{

        String planPwd = userDTO.getPassword();
        String encodedPwd = bCryptPasswordEncoder.encode(planPwd);

        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(encodedPwd)
                .role(userDTO.getRole())
                .oauth(userDTO.getOauth())
                .oauthid(userDTO.getOauthid())
                .build();

        User savedUser = null;

        try{
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("fail to join");
        }

        log.info("savedUser=======>" + savedUser);

        return new UserDTO().makeEntityToDto(savedUser);
    }

    @Transactional
    public UserDTO loginUser(UserDTO userDTO) throws Exception{

        User user = userRepository.findByEmail(userDTO.getEmail());

        log.info("loginUser=======>" + user);

        boolean isValid = false;

        if(user != null) {
            isValid = bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword());
        }

        if(isValid) {

            String token = JwtUtil.createToken(userDTO.getEmail(), secretKey, expireTimeMs);

            log.info("valid loginUser token=======>" + token);

            UserDTO returnDTO = new UserDTO().makeEntityToDto(user);
            returnDTO.setToken(token);

            return returnDTO;
        } else {
            throw new RuntimeException("fail to login");
        }
    }

    @Transactional
    public void logoutUser(UserDTO userDTO) {

        //로그아웃 처리

    }
}
