package entities;

public enum RoomNumType {
    //三种类型是什么 已经很明显了（
    forty(40),
    sixty(60),
    two_hundred(200);

    //构造函数为枚举赋值
    private final int value;
    RoomNumType(int i) {
        value = i;
    }
    //赋值后调用该方法获取枚举数值
    public int getValue() {
        return value;
    }
}
