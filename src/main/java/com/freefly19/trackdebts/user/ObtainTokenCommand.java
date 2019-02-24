package com.freefly19.trackdebts.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ObtainTokenCommand {
    @ApiModelProperty(example = "user@gmail.com")
    private String email;

    @ApiModelProperty(example = "password")
    private String password;
}
