package cn.hzr0523.decorator;

/**
 * 定义一个房子基类
 * hezhi
 * 2018/3/1 14:52
 */
public abstract class House {
    protected String name;


    public String getName() {
        return this.name;
    }

    public abstract double getPrice();
}
