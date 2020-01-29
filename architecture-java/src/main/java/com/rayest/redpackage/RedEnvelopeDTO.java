package com.rayest.redpackage;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RedEnvelopeDTO {
    private String userNo;
    private String totalAmount;
    private String totalNumber;
}
