package cn.hzr0523.decorator;

/**
 * hezhi
 * 2018/3/1 15:13
 */
public class HouseDecorator {
    public static void main(String args[]) {
        House house = new MyHouse();
        DecoratorNeeded needed1 = new TVDeco(new LightDeco(house));
        DecoratorNeeded needed2 = new TVDeco(new LightDeco(new SofaDeco(house)));

        System.out.println(needed1.getName());
        System.out.println("装修花费：" + needed1.getPrice());

        System.out.println(needed2.getName());
        System.out.println("装修花费：" + needed2.getPrice());
    }
}
