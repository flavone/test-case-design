package com.cfpamf.test.design.service;

/**
 *
 * @author flavone
 * @date 2018/09/26
 */
public interface IGetCaseService<REQ, RESP> {

    /**
     * 获取测试用例
     * @param inputData 入参
     * @return 返回结果
     */
    RESP getTestCase(REQ inputData);
}
