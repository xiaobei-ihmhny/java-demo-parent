package com.xiaobei.easyexcel.demo;

import com.alibaba.excel.EasyExcel;
import com.xiaobei.easyexcel.demo.domain.SkuInfo;
import org.junit.Test;

import java.util.List;

/**
 * excel 导出测试用例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-13 14:57:57
 */
public class WriteTest {

    @Test
    public void testSimpleWrite() {
        // 此 excel 表格可以为空，不为空时会自动覆盖
        String fileName = "D:\\skuInfo.xlsx";
        EasyExcel.write(fileName, SkuInfo.class).sheet(0).doWrite(listSkuInfo());
    }

    /**
     * 具体获取数据的逻辑
     * @return
     */
    private List<SkuInfo> listSkuInfo() {
        return null;
    }
}
