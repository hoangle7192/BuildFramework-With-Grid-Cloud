package MOT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class demoMOT {
    static String ngaythang = "2022年5月30日(月)\n" +
            "9:00-10:15";

    public static void main(String[] args) {
        tinhTien();

    }

    public static void tinhTien() {
        String[] a = ngaythang.split("\n");
        System.out.println("a1 " + a[0]);
        System.out.println("a2 " + a[1]);

        String[] timeXuLy1 = a[1].split("-");
        String timeXuLy11 = timeXuLy1[0];
        String timeXuLy12 = timeXuLy1[1];
        System.out.println("timeXuLy1-1 " + timeXuLy11);
        System.out.println("timeXuLy1-2 " + timeXuLy12);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date checkIn = null;
        Date checkOut = null;
        try {
            checkIn = dateFormat.parse(timeXuLy11);
            checkOut = dateFormat.parse(timeXuLy12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("checkIn: " + checkIn);
        System.out.println("checkOut: " + checkOut);

        assert checkOut != null;
        long diff = checkOut.getTime() - checkIn.getTime();

        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diff);
        System.out.print("minutes: " + minutes);


        /*System.out.println("totalGio: " + diff);

        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;

        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");*/




        /*String timeXuLy10 = timeXuLy1[0].replace(":", ".");
        String timeXuLy11 = timeXuLy1[1].replace(":", ".");
        System.out.println("timeXuLy10: " + timeXuLy10);
        System.out.println("timeXuLy11: " + timeXuLy11);

        float timeXuLy101 = Float.parseFloat(timeXuLy10);
        float timeXuLy102 = Float.parseFloat(timeXuLy11);
        System.out.println("timeXuLy101: " + timeXuLy101);
        System.out.println("timeXuLy102: " + timeXuLy102);

        float totalGio = timeXuLy102 - timeXuLy101;
        System.out.println("totalGio: " + totalGio);

        int soPhut = (int) chuyenDoiRaPhut(totalGio);
        int tongSoTien = soPhut * giaTienCho1P(330);

        System.out.println("tongSoTien: " + tongSoTien);*/

    }

    public static float chuyenDoiRaPhut(float gio) {
        return gio*60;
    }

    public static int giaTienCho1P(int giaTienCho15P) {
        return giaTienCho15P/15;
    }
}
