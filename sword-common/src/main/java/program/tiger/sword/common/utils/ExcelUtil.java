package program.tiger.sword.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.multipart.MultipartFile;
import program.tiger.sword.enums.ExcelTypeEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author junhu.li
 * @ClassName ExcelUtil
 * @Description TODO
 * @date 2019-08-2211:17
 * @Version 1.0.0
 */

public class ExcelUtil {
    /**
     * 要解析excel中的列名
     */
    private static List<String> columns;
    /**
     * 要解析的sheet下标
     */
    private static int sheetNum = 0;
    /**
     * 是否
     */
    private static boolean dynamicColumn = false;
    /**
     * 动态列配置文件
     */
    private static Map<String, String> dynamicMapConfig = new ListOrderedMap<>();

    /**
     * poi读取excle
     *
     * @return
     */
    public static String readExcel(File file) throws Exception {
        StringBuilder retJson = new StringBuilder();
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inStream);
            HSSFSheet sheet = workbook.getSheetAt(sheetNum);//获得表
            int lastRowNum = sheet.getLastRowNum();//最后一行
            retJson.append("[");
            for (int i = 0; i < lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i + 1);//获得行
                String rowJson = readExcelRow(row);
                retJson.append(rowJson);
                if (i < lastRowNum - 1) {
                    retJson.append(",");
                }
            }
            retJson.append("]");
        } catch (Exception e) {
            try {
                inStream = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(inStream);
                XSSFSheet sheet = workbook.getSheetAt(sheetNum);
                int lastRowNum = sheet.getLastRowNum();//最后一行
                retJson.append("[");
                for (int i = 0; i < lastRowNum; i++) {
                    XSSFRow row = sheet.getRow(i + 1);//获得行
                    String rowJson = readExcelRow(row);
                    retJson.append(rowJson);
                    if (i < lastRowNum - 1) {
                        retJson.append(",");
                    }
                }
                retJson.append("]");
            } catch (IOException e1) {
                e1.printStackTrace();
                throw e1;
            }
        } finally {
            close(null, inStream);
        }
        return retJson.toString();
    }

    /**
     * poi读取excle 生成实体集合
     *
     * @param <E>
     * @return
     */
    public static <E> List<E> readExcel(File file, Class<E> clazz) throws Exception {
        if (columns == null) {
            setColumns(clazz);
        }
        InputStream inStream = null;
        List<E> eList = new ArrayList<E>();
        try {
            inStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inStream);
            HSSFSheet sheet = workbook.getSheetAt(sheetNum);//获得表
            int lastRowNum = sheet.getLastRowNum();//最后一行
            if (dynamicColumn) {
                //动态列處理
                HSSFRow header = sheet.getRow(0);//表頭
                List<String> rList = readRow(header);
                setDynamicColumn(rList);
            }
            for (int i = 0; i < lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i + 1);//获得行
                String rowJson = readExcelRow(row);
                E _e = JsonUtil.toBean(rowJson, clazz);
                eList.add(_e);
            }
        } catch (Exception e) {
            try {
                inStream = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(inStream);
                XSSFSheet sheet = workbook.getSheetAt(sheetNum);
                int lastRowNum = sheet.getLastRowNum();//最后一行
                if (dynamicColumn) {
                    //动态列處理
                    XSSFRow header = sheet.getRow(0);
                    List<String> rList = readRow(header);
                    setDynamicColumn(rList);
                }
                for (int i = 0; i < lastRowNum; i++) {
                    XSSFRow row = sheet.getRow(i + 1);//获得行
                    String rowJson = readExcelRow(row);
                    E _e = JsonUtil.toBean(rowJson, clazz);
                    eList.add(_e);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                throw e1;
            }
        } finally {
            close(null, inStream);
        }
        return eList;
    }

    /**
     * 动态列配置
     */
    private static void setDynamicColumn(List<String> headlist) throws Exception {
        List<String> tempcolumns = new ArrayList<String>();
        for (Iterator<String> iterator = headlist.iterator(); iterator.hasNext(); ) {
            String value = iterator.next();
            value = value.replace("（", "(");
            value = value.replace("）", ")");
            value = value.trim();
            String key = findKey(value);
            if (key == null) {
                throw new RuntimeException("请上传合法excle！");
            }
            tempcolumns.add(findKey(value));
        }
        setColumns(tempcolumns);//重新设置column
    }

    /**
     * 根据value动态找到key值
     *
     * @return
     */
    private static String findKey(String value) {
        for (Map.Entry<String, String> entryconfig : dynamicMapConfig.entrySet()) {
            String keyc = entryconfig.getKey();
            String valc = entryconfig.getValue();
            if (valc.equals(value)) {
                return keyc;
            }
        }
        return null;
    }

    /**
     * poi读取excle 生成实体集合
     *
     * @param file
     * @param clazz
     * @param exceptscolumns
     * @return
     */
    public static <E> List<E> readExcel(File file, Class<E> clazz, String[] exceptscolumns) throws Exception {
        setColumns(clazz, exceptscolumns);
        return readExcel(file, clazz);
    }

    /**
     * 读取excle多个sheet到多个对象（对象的顺序固定）
     *
     * @param file
     * @param clazz
     * @return
     */
    public static <E> List<List<E>> readExcel(File file, Class<E>[] clazz) throws Exception {
        List<List<E>> eliLists = new ArrayList<List<E>>();//[clazz.length];
        int i = 0;
        for (Class<E> cls : clazz) {
            setColumns(null, null);
            setSheetNum(i++);
            List<E> eList = readExcel(file, cls);
            eliLists.add(eList);
        }
        return eliLists;
    }

    /**
     * 读取行值
     *
     * @return
     */
    private static String readExcelRow(HSSFRow row) {
        StringBuilder rowJson = new StringBuilder();
        int lastCellNum = ExcelUtil.columns.size();//最后一个单元格
        rowJson.append("{");
        for (int i = 0; i < lastCellNum; i++) {
            HSSFCell cell = row.getCell(i);
            String cellVal = readCellValue(cell);
            rowJson.append(toJsonItem(columns.get(i), cellVal));
            if (i < lastCellNum - 1) {
                rowJson.append(",");
            }
        }
        rowJson.append("}");
        return rowJson.toString();
    }

    /**
     * 读取行值
     *
     * @return
     */
    private static String readExcelRow(XSSFRow row) {
        StringBuilder rowJson = new StringBuilder();
        int lastCellNum = ExcelUtil.columns.size();//最后一个单元格
        rowJson.append("{");
        for (int i = 0; i < lastCellNum; i++) {
            XSSFCell cell = row.getCell(i);
            String cellVal = readCellValue(cell);
            rowJson.append(toJsonItem(columns.get(i), cellVal));
            if (i < lastCellNum - 1) {
                rowJson.append(",");
            }
        }
        rowJson.append("}");
        return rowJson.toString();
    }

    /**
     * 读取行生称list
     *
     * @return
     */
    private static List<String> readRow(XSSFRow row) {
        List<String> ret = new ArrayList<String>();
        int cellcount = row.getLastCellNum();
        for (int i = 0; i < cellcount; i++) {
            XSSFCell cell = row.getCell(i);
            String cellval = readCellValue(cell);
            if (cellval.trim().length() > 0) {
                ret.add(cellval);
            }
        }
        return ret;
    }

    /**
     * 读取行生称list
     *
     * @return
     */
    private static List<String> readRow(HSSFRow row) {
        List<String> ret = new ArrayList<String>();
        int cellcount = row.getLastCellNum();
        for (int i = 0; i < cellcount; i++) {
            HSSFCell cell = row.getCell(i);
            String cellval = readCellValue(cell);
            if (cellval.trim().length() > 0) {
                ret.add(cellval);
            }
        }
        return ret;
    }

    /**
     * 读取单元格的值
     *
     * @param cell
     * @return
     */
    private static String readCellValue(Cell cell) {
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
            SimpleDateFormat sdf = null;
            String str_temp = "";
            if (format == 14 || format == 31 || format == 57 || format == 58) {
                //日期
                sdf = new SimpleDateFormat("yyyy/MM/dd");
                double value = cell.getNumericCellValue();
                Date date = DateUtil.getJavaDate(value);
                str_temp = sdf.format(date);
            } else if (format == 10) {
                //百分比
                str_temp = cell.getNumericCellValue() + "";
            } else {
                str_temp = cell.getRichStringCellValue().getString();
                cell.setCellValue(str_temp);//设置为String
            }
            return str_temp;
        } else if (cell.getCellType() == CellType.FORMULA) {
            CellType val = cell.getCachedFormulaResultType();
            return val + "";
        } else {
            return String.valueOf(cell.getRichStringCellValue());
        }
    }

    /**
     * 转换为json对
     *
     * @return
     */
    private static String toJsonItem(String name, String val) {
        return "\"" + name + "\":\"" + val + "\"";
    }

    /**
     * 关闭io流
     *
     * @param out
     * @param in
     */
    private static void close(OutputStream out, InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("InputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println("OutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 填出数据到excel book中
     *
     * @param book
     * @param data
     * @param sheetname
     * @param titles
     * @param columns
     */
    public static void data2Book(Workbook book, List<? extends Object> data, String sheetname, List<String> titles, List<String> columns) throws Exception {
        Sheet sheet = book.createSheet(sheetname);
        Row th = sheet.createRow((short) 0);//标题行
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = th.createCell(i);
            cell.setCellValue(titles.get(i));
        }
        Object _d = data.get(0);
        if (_d instanceof Map) {
            //Map集合
            for (int j = 0; j < data.size(); j++) {
                Map _dm = (Map) data.get(j);
                Row tr = sheet.createRow((short) (j + 1));//内容行
                for (int k = 0; k < columns.size(); k++) {
                    Cell cell = tr.createCell(k);
                    Object val = _dm.get(columns.get(k));
                    String value = val == null ? "" : val.toString();
                    cell.setCellValue(value);
                }

            }
        } else {
            //Bean集合
            for (int j = 0; j < data.size(); j++) {
                Object _do = data.get(j);
                Row tr = sheet.createRow((short) (j + 1));//内容行
                for (int k = 0; k < columns.size(); k++) {
                    String column = columns.get(k);
                    Method method = getTargetGetMethod(_do, column);//获取目标方法
                    try {
                        Cell cell = tr.createCell(k);
                        Object val = method.invoke(_do);
                        String value = val == null ? "" : val.toString();
                        value = value.replace("00:00:00.0", "");//处理时间中非法字符
                        cell.setCellValue(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }

            }
        }
    }

    /**
     * 获取bean的指定getter方法
     *
     * @param o
     * @param name
     * @return
     */
    private static Method getTargetGetMethod(Object o, String name) throws Exception {
        try {
            String mname = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            return o.getClass().getMethod(mname);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 将bean所有属性放入map中
     */
    private static <E> void beanProp2List(Class<E> clazz, List<String> excepts) {
        Field[] fields = clazz.getDeclaredFields();
        columns = new ArrayList<>();//顺序固定可重复
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            if (excepts != null && excepts.contains(fieldName)) {
                continue;
            }
            columns.add(fieldName);
        }
    }

    public static List<String> getColumns() {
        return ExcelUtil.columns;
    }

    public static void setColumns(List<String> columns) {
        ExcelUtil.columns = columns;
    }

    public static void setColumns(Class<?> clazz) {
        beanProp2List(clazz, null);
    }

    /**
     * 设置列，不包括excepts指定的字段
     *
     * @param clazz
     * @param excepts
     */
    public static void setColumns(Class<?> clazz, String[] excepts) {
        beanProp2List(clazz, Arrays.asList(excepts));
    }

    public static int getSheetNum() {
        return sheetNum;
    }

    public static void setSheetNum(int sheetNum) {
        ExcelUtil.sheetNum = sheetNum;
    }

    public static boolean isDynamicColumn() {
        return dynamicColumn;
    }

    public static void setDynamicColumn(boolean dynamicColumn) {
        ExcelUtil.dynamicColumn = dynamicColumn;
    }

    public static Map<String, String> getDynamicMapConfig() {
        return dynamicMapConfig;
    }

    public static void setDynamicMapConfig(Map<String, String> dynamicMapConfig) {
        ExcelUtil.dynamicMapConfig = dynamicMapConfig;
    }


    /**
     * excel 导出
     *
     * @param list           数据
     * @param title          标题
     * @param sheetName      sheet名称
     * @param pojoClass      pojo类型
     * @param fileName       文件名称
     * @param isCreateHeader 是否创建表头
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, ServerHttpResponse response) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * excel 导出
     *
     * @param list      数据
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  文件名称
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, ServerHttpResponse response) throws IOException {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
    }

    /**
     * excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param fileName     文件名称
     * @param response
     * @param exportParams 导出参数
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, ExportParams exportParams, ServerHttpResponse response) throws IOException {
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * excel 导出
     *
     * @param list     数据
     * @param fileName 文件名称
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, ServerHttpResponse response) throws IOException {
        defaultExport(list, fileName, response);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param fileName     文件名称
     * @param response
     * @param exportParams 导出参数
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, ServerHttpResponse response, ExportParams exportParams) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list     数据
     * @param fileName 文件名称
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, ServerHttpResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 下载
     *
     * @param fileName 文件名称
     * @param response
     * @param workbook excel数据
     */
    private static void downLoadExcel(String fileName, ServerHttpResponse response, Workbook workbook) throws IOException {
        try {
            ZeroCopyHttpOutputMessage zeroCopyHttpOutputMessage = (ZeroCopyHttpOutputMessage) response;
            response.getHeaders().setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            response.getHeaders().set("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            DataBuffer dbf = response.bufferFactory().wrap(bos.toByteArray());
            zeroCopyHttpOutputMessage.writeWith(s -> s.onNext(dbf));
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param filePath   excel文件路径
     * @param titleRows  标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedSave(true);
        params.setSaveUrl("/excel/");
        try {
            return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("模板不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param file      excel文件
     * @param pojoClass pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) throws IOException {
        return importExcel(file, 1, 1, pojoClass);
    }

    /**
     * excel 导入
     *
     * @param file       excel文件
     * @param titleRows  标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        return importExcel(file, titleRows, headerRows, false, pojoClass);
    }

    /**
     * excel 导入
     *
     * @param file       上传的文件
     * @param titleRows  标题行
     * @param headerRows 表头行
     * @param needVerfiy 是否检验excel内容
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, boolean needVerfiy, Class<T> pojoClass) throws IOException {
        if (file == null) {
            return null;
        }
        try {
            return importExcel(file.getInputStream(), titleRows, headerRows, needVerfiy, pojoClass);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param inputStream 文件输入流
     * @param titleRows   标题行
     * @param headerRows  表头行
     * @param needVerify  是否检验excel内容
     * @param pojoClass   pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows, boolean needVerify, Class<T> pojoClass) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setSaveUrl("/excel/");
        params.setNeedSave(true);
        params.setNeedVerify(needVerify);
        try {
            return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("excel文件不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

}
