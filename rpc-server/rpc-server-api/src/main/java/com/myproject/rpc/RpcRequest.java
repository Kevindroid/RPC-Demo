package com.myproject.rpc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 传输对象
 */
@Getter
@Setter
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 7811779408856528992L;
    private String className;

    private String methodName;

    private Object[] params;
}
