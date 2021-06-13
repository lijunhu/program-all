package program.tiger.sword.enums;

/**
 * Excel 类型枚举
 */
public enum ExcelTypeEnum {

    /**
     * excle2003
     */
    XLS("xls"),
    /**
     * excle2007
     */
    XLSX("xlsx");

    private final String value;

    /**
     * 构造函数
     *
     * @param value value
     */
    ExcelTypeEnum(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
