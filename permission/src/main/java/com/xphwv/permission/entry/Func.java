package com.xphwv.permission.entry;

import java.io.Serializable;

public class Func implements Serializable {
	private static final long serialVersionUID = -8436155927856087373L;
	private int funcId;
	private String code;

	public Func(int funcId, String code) {
		super();
		this.funcId = funcId;
		this.code = code;
	}

	public int getFuncId() {
		return funcId;
	}

	public void setFuncId(int funcId) {
		this.funcId = funcId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
