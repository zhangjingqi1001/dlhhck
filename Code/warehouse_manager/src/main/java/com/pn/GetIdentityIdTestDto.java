package com.pn;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIdentityIdTestDto implements Serializable {
    private static final long serialVersionUID = 3293035228265414449L;

    @NotBlank
    @JSONField(name = "identityCard",ordinal = 1)
    private String identityCard;

    @NotBlank
    @JSONField(name = "Sex",ordinal = 2)
    private String Sex;

//   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date date;

}
