package model;

/**
 * @author dengyq on 2018/7/24 下午4:53
 */
public class Poker implements Comparable{

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

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return number == ((Poker) obj).number && type == ((Poker) obj).type;
    }

    @Override
    public int compareTo(Object obj) {
        Poker poker = (Poker) obj;
        if (number == poker.getNumber()) {
            return type > poker.getType() ? 1 : -1;
        }
        if (number == 3) return 1;
        if (poker.getNumber() == 3) return -1;
        if (number == 2 && poker.getNumber() !=3 ) return 1;
        if (poker.getNumber() == 2 && number !=3 ) return -1;
        if (number == 1 && (poker.getNumber() != 3 || poker.getNumber() !=2)) return 1;
        if (poker.getNumber() == 1 && (number != 3 || number !=2)) return -1;
        return number > poker.getNumber() ? 1 : -1;
    }
}
