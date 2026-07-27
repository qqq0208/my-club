package com.md.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 油桃老师
 **/
public class EasyExcelUtil {
    //定义每个表格最大数据量（行）
    private static final int BATCH_SIZE = 100;

    /**
     * 下载excel文件的方法
     *
     * @param resp     响应对象
     * @param fileName 文件名字
     * @param data     数据源
     */
    @SneakyThrows
    public static void download(HttpServletResponse resp, String fileName, Collection<?> data) {
        //数据非空判断
        if (CollectionUtil.isEmpty(data)) return;
        //处理文件名字 如果文件名字不是.xlsx为后缀 强制改成该后缀
        fileName = fileName.endsWith(".xlsx") ? fileName : fileName + ".xlsx";
        //对文件名字重新编码 避免文件名中文乱码问题
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        //设置响应头
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreasheetml.Sheet;charset=utf-8");
        resp.setHeader("Content-disposition", "attachment;filename=" + fileName);
        //获取数据的类型
        Class<?> type = data.iterator().next().getClass();
        //准备数据分批
        List<Object> allData = new ArrayList<>(data);
        int totalSize = allData.size();
        //定义索引
        int sheetIndex = 1;
        //创建一个写入器
        try (ExcelWriter excelWriter = EasyExcel.write(resp.getOutputStream(), type).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build()) {
            //循环分片处理
            for (int i = 0; i < totalSize; i += BATCH_SIZE) {
                //计算当前分片的结束索引、确保结束索引不会超过数据总量、避免数据越界
                int end = Math.min(i + BATCH_SIZE, totalSize);
                //截取当前分片数据： 从索引1到end（不包含）
                List<Object> batchData = allData.subList(i, end);
                //创建单元格 指定索引和名称
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetIndex, "sheet" + String.format("%02d", sheetIndex)).build();
                //将当前数据写入当前单元格
                excelWriter.write(batchData,writeSheet);
                //sheet索引+1
                sheetIndex++;
            }
        }

    }
}
