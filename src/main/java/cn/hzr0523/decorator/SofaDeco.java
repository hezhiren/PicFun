package cn.hzr0523.decorator;

/**
 * 沙发
 * hezhi
 * 2018/3/1 15:34
 */
public class SofaDeco extends DecoratorNeeded {
    House house;
    public SofaDeco(House h) {
        this.house = h;
    }

    @Override
    public String getName() {
        return house.getName() + " 安装沙发";
    }

    @Override
    public double getPrice() {
        return house.getPrice() + 3000;
    }
}
