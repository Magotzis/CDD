package enums;

/**
 * @author dengyq on 2018/7/26 下午7:30
 */
public enum CardType {
    NOT_CARD_TYPE(-1, "错误的牌"),
    SINGLE(0, "单张"),
    PAIR(1, "对子"),
    TRIPLE(2, "三张"),

    FLUSH(3, "顺子"),
    THREE_BELT_TWO(4, "三带二"),
    FOURTH_BELT_ONE(5, "四带一"),
    STRAIGHT_FLUSH(6, "同花顺");

    private int code;
    private String message;

    CardType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean inRanger(CardType begin, CardType end) {
        if (begin.code > end.code) {
            return false;
        }
        return code >= begin.code && (code <= end.code);
    }
}
