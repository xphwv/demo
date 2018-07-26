/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th.play;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存历史记录的数据结构，有一个List<Integer>字段。
 * @author XuPan
 * @date 2014-5-9 下午4:16:39
 * @version 1.0
 */
public class Record {
	private  List<Integer> attemptList=new ArrayList<Integer>();
	public void save( Integer value){
		attemptList.add(value);
	}
	public boolean contains(int value) {
        return attemptList.contains(value);
    }
	@Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(attemptList.size() + " Times: ");
        int count = 1;
        for(Integer attempt : attemptList) {
            buffer.append(attempt);
            if(count < attemptList.size()) {
                buffer.append(", ");
                count++;
            }
        }
        return buffer.toString();
    }
}
