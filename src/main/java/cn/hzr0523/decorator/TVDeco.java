package cn.hzr0523.decorator;

/**
 * 电视
 * hezhi
 * 2018/3/1 15:01
 */
public class TVDeco extends DecoratorNeeded{
    House house;

    public TVDeco(House h) {
        this.house = h;
    }

    @Override
    public String getName() {
        return house.getName() + " 安装电视";
    }

    @Override
    public double getPrice() {
        return house.getPrice() + 4999;
    }
}
