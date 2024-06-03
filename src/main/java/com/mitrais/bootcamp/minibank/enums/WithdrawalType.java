package com.mitrais.bootcamp.minibank.enums;

public enum WithdrawalType {

    /**
     * WITHDRAWAL Type
     */
    COMMON ("COMMON", "COMMON WITHDRAWAL"),
    OTHER ("OTHER", "OTHER WITHDRWAL")
    ;

    private String code;
    private String name;

    WithdrawalType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static WithdrawalType getByCode(String code) {
        WithdrawalType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            WithdrawalType item = var1[var3];
            if (item.getCode().equalsIgnoreCase(code)) {
                return item;
            }
        }

        return null;
    }
}