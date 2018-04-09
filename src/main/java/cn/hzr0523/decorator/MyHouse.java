package cn.hzr0523.decorator;

/**
 * 被装饰者的初始状态，初始花费0元
 * hezhi
 * 2018/3/1 14:54
 */
public class MyHouse extends House {

    public MyHouse() {
        name = "我的房子";
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
