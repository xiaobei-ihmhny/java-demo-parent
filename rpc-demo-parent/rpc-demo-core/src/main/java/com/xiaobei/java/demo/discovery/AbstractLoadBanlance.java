package com.xiaobei.java.demo.discovery;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 20:36:36
 */
public abstract class AbstractLoadBanlance implements LoadBalance {

    @Override
    public String select(List<String> serviceList) {
        if (null == serviceList || serviceList.size() == 0) {
            return null;
        } else if (serviceList.size() == 1) {
            return serviceList.get(0);
        }
        return selectOne(serviceList);
    }

    protected abstract String selectOne(List<String> serviceList);
}