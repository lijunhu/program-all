package program.tiger.sword.enums;

import lombok.Getter;


/**
 * Excel 类型枚举
 */
@Getter
public enum ExcelTypeEnum {

    /**
     * excle2003
     */
    XLS("xls"),
    /**
     * excle2007
     */
    XLSX("xlsx");
    private String value;

    /**
     * 构造函数
     *
     * @param value
     */
    ExcelTypeEnum(String value) {
        this.value = value;
    }

}
