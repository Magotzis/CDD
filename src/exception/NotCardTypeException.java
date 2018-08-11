package exception;

/**
 * @author dengyq on 2018/8/11 上午11:29
 */
public class NotCardTypeException extends BaseException {
    public NotCardTypeException() {
        super("不是合理的卡牌类型");
    }
}
