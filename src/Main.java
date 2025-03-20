import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.time.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Locale;
import java.time.format.TextStyle;

public class Main {
    public static void ex2 (LocalDate first, LocalDate second){
        if (first.isAfter(second)){
            System.out.println(first + " is after than " + second);
        }
        else if (first.isBefore(second)){
            System.out.println(first + " is before than " + second);
        }
        else if (first.isEqual(second)){
            System.out.println(first + " is equal " + second);
        }
    }
    public static long ex3 (){
        LocalDate today = LocalDate.now();
        LocalDate newYear = LocalDate.of(today.getYear() + 1, 1, 1);
        return ChronoUnit.DAYS.between(today, newYear);
    }
    public static boolean ex4(int year){
        if (Year.of(year).isLeap()){
            return true;
        }
        else return false;
    }
    public static int ex5 (int month, int year){
        int weekends = 0;
        LocalDate date = LocalDate.of(year, month, 1);
        while (date.getMonthValue() == month) {
            DayOfWeek day = date.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                weekends++;
            }
            date = date.plusDays(1);
        }
        return weekends;
    }
    public static void ex6() {
        long startMilli = System.currentTimeMillis();
        Task();
        long endMilli = System.currentTimeMillis();
        System.out.println("время выполнения миллисекунд " + (endMilli - startMilli));
    }
    public static void Task() {
        long sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }
        System.out.println("результат: " + sum);
    }
    public static LocalDate ex7(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate date = LocalDate.parse(inputDate, inputFormatter);
            LocalDate newDate = date.plusDays(10);
            return newDate;
        } catch (Exception e) {
            System.out.println("некорректный формат даты");
            return null;
        }
    }
    public static LocalDateTime ex8(LocalDateTime dateTime) {
        ZonedDateTime utcZoned = dateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime moscowZoned = utcZoned.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        return moscowZoned.toLocalDateTime();
    }
    public static int ex9 (LocalDate date) {
        LocalDate today = LocalDate.now();
        return Period.between(date, today).getYears();
    }
    public static void ex10 (int mounth, int year) {
        LocalDate date = LocalDate.of(year, mounth, 1);
        String typeOfDate;
        while (date.getMonthValue() == mounth) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                typeOfDate = "выходной";
            }
            else typeOfDate = "рабочий";
            System.out.println(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " - " + typeOfDate);
            date = date.plusDays(1);
        }
    }
    public static LocalDate ex11 (LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomDay);
    }
    public static void ex12 (LocalDate date, LocalTime Time) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime DateTime = LocalDateTime.of(date, Time);
        if (DateTime.isBefore(today)) {
            throw new IllegalArgumentException("событие уже прошло");
        }
        Duration duration = Duration.between(today, DateTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        System.out.println("до события осталось: " + hours + " часов, " + minutes + " минут, " + seconds + " секунд");
    }
    public static long ex13(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("начало рабочего дня должно быть раньше конца");
        }
        long workHours = 0;
        while (start.isBefore(end)) {
            DayOfWeek dayOfWeek = start.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                LocalDateTime nextHour = start.plusHours(1);
                if (nextHour.isAfter(end)) {
                    workHours += ChronoUnit.MINUTES.between(start, end) / 60.0;
                } else {
                    workHours++;
                }
            }
            start = start.plusHours(1);
        }
        return workHours;
    }
    public static String ex14(LocalDate date, String local) {
        Locale locale = new Locale(local);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", locale);
        return date.format(formatter);
    }
    public static String ex15(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru"));
    }

    public static void main(String[] args) {
        //1
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("текущая дата: "+ date.format(dateFormatter));
        System.out.println("текущее время: "+ time.format(timeFormatter));
        //2
        ex2(date, date);
        ex2(date.plusDays(1), date);
        ex2(date.minusDays(2), date);
        //3
        System.out.println("до нового года осталось " + ex3());
        //4
        System.out.println("введите год: ");
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        System.out.println(ex4(year));
        //5
        System.out.println("введите месяц и год в цифрах: ");
        int month = scanner.nextInt();
        year = scanner.nextInt();
        System.out.println("Выходные в этом месяце " + ex5(month, year));
        //6
        ex6();
        //7
        System.out.println("введите строку даты в формате dd-MM-yyyy: ");
        String dat = scanner.next();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        System.out.println(ex7(dat).format(outputFormatter));
        //8
        LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);
        System.out.println("UTC: " + nowUtc);
        System.out.println("московское время: " + ex8(nowUtc));
        //9
        LocalDate birthDate = LocalDate.of(2006, 3, 10);
        System.out.println("возраст: " + ex9(birthDate));
        //10
        ex10(3,2025);
        //11
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);
        System.out.println("рандомная дата: " + ex11(start, end));
        //12
        LocalDate day = LocalDate.of(2025,7,29);
        LocalTime ttime = LocalTime.of(0, 0, 0);
        ex12(day, ttime);
        //13
        LocalDateTime tart = LocalDateTime.of(2024, 3, 11, 9, 0);
        LocalDateTime endd = LocalDateTime.of(2024, 3, 15, 18, 0);
        System.out.println("рабочие часы: " + ex13(tart, endd));
        //14
        System.out.println("дата на русском: " + ex14(date, "ru"));
        System.out.println("дата на английском: " + ex14(date, "en"));
        //15
        System.out.println(ex15(date));
    }
}