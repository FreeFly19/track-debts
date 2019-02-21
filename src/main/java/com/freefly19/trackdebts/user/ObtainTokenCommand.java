package com.freefly19.trackdebts.user;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

//@Builder
//@Value
@NoArgsConstructor
@Getter
public class ObtainTokenCommand {
    @ApiModelProperty(example = "user@gmail.com")
    private String email;

    @ApiModelProperty(example = "password")
    private String password;
}
