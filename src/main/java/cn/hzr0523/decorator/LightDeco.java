package cn.hzr0523.decorator;

/**
 * 灯
 * hezhi
 * 2018/3/1 15:04
 */
public class LightDeco extends DecoratorNeeded {
    House house ;

    public LightDeco(House h) {
        this.house = h;
    }

    @Override
    public String getName() {
        return house.getName() + " 安装灯";
    }

    @Override
    public double getPrice() {
        return house.getPrice() + 1000;
    }
}
