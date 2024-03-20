package com.mycompany.common.value_set;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 19:31
 * @注释
 */

public enum MemberPrivilegeCode {
    UNACTIVATED("不能使用基本功能", 0),
    ACTIVATED("可以进行买卖", 1),
    MANAGER("拥有所有权限", 2);

    private String privilege;
    private int level;
    private MemberPrivilegeCode(String privilege, int level){
        this.privilege = privilege;
        this.level = level;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
