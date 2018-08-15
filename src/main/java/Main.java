import com.gdut.CDD.utils.QRCode.QRCodeFactory;
import com.gdut.CDD.model.Poker;
import com.gdut.CDD.utils.SortCompator;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Poker> list = new ArrayList<>(52);
        for (int i = 0; i < 13; i++) {
            list.add(new Poker(i + 1, 1));
            list.add(new Poker(i + 1, 2));
            list.add(new Poker(i + 1, 3));
            list.add(new Poker(i + 1, 4));
        }
        Collections.shuffle(list);
        Poker[] a = new Poker[13];
        Poker[] b = new Poker[13];
        Poker[] c = new Poker[13];
        Poker[] d = new Poker[13];
        for (int i = 0; i < 13; i++) {
            a[i] = list.get(i);
        }
        for (int j = 0, i = 13; i < 26; j++, i++) {
            b[j] = list.get(i);
        }
        for (int j = 0, i = 26; i < 39; j++, i++) {
            c[j] = list.get(i);
        }
        for (int j = 0, i = 39; i < 52; j++, i++) {
            d[j] = list.get(i);
        }
        Arrays.sort(a, new SortCompator());
        Arrays.sort(b, new SortCompator());
        Arrays.sort(c, new SortCompator());
        Arrays.sort(d, new SortCompator());
        String contentA = printPoker(a);
        String contentB = printPoker(b);
        String contentC = printPoker(c);
        String contentD = printPoker(d);
        String logUriA = "img/yu.png";
        String logUriB = "img/wz.png";
        String logUriC = "img/yq.png";
        String logUriD = "img/++.png";
        String outFileUriA = "yu.jpg";
        String outFileUriB = "wz.jpg";
        String outFileUriC = "yq.jpg";
        String outFileUriD = "++.jpg";
        int[] size = new int[]{430, 430};
        String format = "jpg";

        try {
            new QRCodeFactory().CreatQrImage(contentA, format, outFileUriA, logUriA, size);
            new QRCodeFactory().CreatQrImage(contentB, format, outFileUriB, logUriB, size);
            new QRCodeFactory().CreatQrImage(contentC, format, outFileUriC, logUriC, size);
            new QRCodeFactory().CreatQrImage(contentD, format, outFileUriD, logUriD, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Poker poker = a[0];
//        int i = 0;
//        while(poker.getType() == 4 && (poker.getNumber() == 2 || poker.getNumber() == 1)) {
//            i++;
//        }
//        poker = a[i];
//        if (poker.getType() == 1) System.out.print("♦️");
//        if (poker.getType() == 2) System.out.print("♣️");
//        if (poker.getType() == 3) System.out.print("♥️");
//        if (poker.getType() == 4) System.out.print("♠️️");
//        if (poker.getNumber() == 11) System.out.print("J");
//        else if (poker.getNumber() == 12) System.out.print("Q");
//        else if (poker.getNumber() == 13) System.out.print("K");
//        else System.out.print(poker.getNumber() + " ");
    }

    private static String printPoker(Poker[] pokers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            Poker poker = pokers[i];
            if (poker.getType() == 1) stringBuilder.append("♦️");
            if (poker.getType() == 2) stringBuilder.append("♣️");
            if (poker.getType() == 3) stringBuilder.append("♥️");
            if (poker.getType() == 4) stringBuilder.append("♠️️");
            if (poker.getNumber() == 11) stringBuilder.append("J");
            else if (poker.getNumber() == 12) stringBuilder.append("Q");
            else if (poker.getNumber() == 13) stringBuilder.append("K");
            else stringBuilder.append(poker.getNumber() + " ");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
