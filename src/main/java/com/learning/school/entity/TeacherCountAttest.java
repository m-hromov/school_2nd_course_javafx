
package com.learning.school.entity;

/**
 *
 * @author hromov
 */
public class TeacherCountAttest {
    String name;
    Integer count;
    String exp;

    public TeacherCountAttest(String name, Integer count, String exp) {
        this.name = name;
        this.count = count;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
    
}
