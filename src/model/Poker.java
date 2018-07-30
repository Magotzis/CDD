package model;

/**
 * @author dengyq on 2018/7/24 下午4:53
 */
public class Poker {

    private int number;

    private int type;

    public Poker(int number, int type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "model.Poker{" +
                "number=" + number +
                ", type=" + type +
                '}';
    }
}
