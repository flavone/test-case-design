package com.cfpamf.test.design.dto.resp;

import lombok.Data;

/**
 *
 * @author flavone
 * @date 2018/09/28
 */
@Data
public abstract class BaseRespDto {

    private boolean status;

    private int count;

    private String errMsg;
}
