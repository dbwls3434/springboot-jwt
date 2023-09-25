package org.yujin.myproc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.yujin.myproc.entity.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {

    private long id;
    private String username;
    @NotNull(message = "your email is blank")
    @NotBlank(message = "please input your email")
    @Size(max = 100, message = "email length can't excess 100bytes")
    private String email;
    @NotNull(message = "your password is blank")
    @NotBlank(message = "please input your password")
    private String password;
    private String role;
    private Date createDate;
    private String oauth;
    private String oauthid;
    private String token;

    public UserDTO makeEntityToDto(User entity) {

        UserDTO dto = UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                //.password(entity.getPassword())
                .role(entity.getRole())
                .oauth(entity.getOauth())
                .oauthid(entity.getOauthid())
                .createDate(entity.getCreateDate())
                .build();

        return dto;
    }

    public User makeDtoToEntity(UserDTO dto) {

        User entity = User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .oauth(dto.getOauth())
                .oauthid(dto.getOauthid())
                //.createDate(dto.getCreateDate())
                .build();

        return entity;
    }
}
