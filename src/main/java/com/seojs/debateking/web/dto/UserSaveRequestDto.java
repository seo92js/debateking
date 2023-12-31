package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]+$", message="소문자 a-z, 숫자 0-9 만 사용 가능합니다.")
    @Size(min = 4, max = 15, message="4~15 글자")
    private String username;
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]+$", message="소문자 a-z, 숫자 0-9 만 사용 가능합니다.")
    @Size(min = 4, max = 15, message="4~15 글자")
    private String password;

    @Builder
    public UserSaveRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }
}
