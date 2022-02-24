package com.handyman.handymanbe.domain.technician;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.workedHours.WorkedHours;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class Technician {
    public static final Integer INIT_NORMAL_SHIFT_HOUR = 7;
    public static final Integer END_NORMAL_SHIFT_HOUR = 20;
    public static final Integer INIT_OF_DAY = 0;
    public static final Integer END_OF_DAY = 24;
    public static final Integer MAX_WORKED_HOURS_PER_WEEK = 48;

    private final TechnicianId id;
    private final TechnicianName name;
    private final TechnicianLastName lastName;
    private final List<Report> reports;

    public Technician(TechnicianId id, TechnicianName name, TechnicianLastName lastName, List<Report> reports) {
        Objects.requireNonNull(id,"Technician id can not be null");
        Objects.requireNonNull(name,"Technician name can not be null");
        Objects.requireNonNull(lastName,"Technician last name can not be null");
        Objects.requireNonNull(reports,"Report list can not be null");

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.reports = reports;
    }

    public WorkedHours workedHoursCalculate(List<Report> reports) {

        long workedMinutesInTheReport;
        long normalMinutesWorked = 0L;
        long nightMinutesWorked = 0L;
        long sundayMinutesWorked = 0L;
        long normalExtraMinutesWorked = 0L;
        long nightExtraMinutesWorked = 0L;
        long sundayExtraMinutesWorked = 0L;
        long totalWorkedHours = 0L;

        long dayNightMinutesWorked = 0L;
        long nextDayNightMinutesWorked = 0L;

        //Other way:
        for(Report report : reports) {
            workedMinutesInTheReport = ChronoUnit.HOURS.between(report.getInitDate(), report.getEndDate());

            //Domingos:
            if (report.getInitDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
                sundayMinutesWorked += workedMinutesInTheReport;
                if (totalWorkedHours > 48) {
                    sundayExtraMinutesWorked += sundayMinutesWorked;
                }
            }
            //Lunes a sábados:
            else {
                //Horas normales: 7-20h
                if((report.getInitDate().getHour() >= 7 && report.getInitDate().getHour() < 20)
                        && (report.getEndDate().getHour() >= 7 && report.getEndDate().getHour() < 20)) {
                    normalMinutesWorked += workedMinutesInTheReport;
                    //Horas normales extra: +48h
                    if (totalWorkedHours > 48) {
                        normalExtraMinutesWorked += workedMinutesInTheReport;
                    }
                }
                //Horas nocturnas de 0 a 6:59:59
                if((report.getInitDate().getHour() >= 0 && report.getInitDate().getHour() < 7)
                        && (report.getEndDate().getHour() >= 0 && report.getEndDate().getHour() < 7)) {
                    dayNightMinutesWorked += 17 - workedMinutesInTheReport;
                    //Horas nocturnas extra madrugada: +48h
                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += dayNightMinutesWorked;
                    }

                }
                //Horas nocturnas de 20 a 23:59:59:
                if((report.getInitDate().getHour() >= 20 && report.getInitDate().getHour() < 24)
                        && (report.getEndDate().getHour() >= 20 && report.getEndDate().getHour() < 24)) {
                    nightMinutesWorked += 20 - workedMinutesInTheReport;
                    //Horas nocturnas extra madrugada: +48h
                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += nightMinutesWorked;

                    }
                }
                //Horas entre nocturnas de madrugada y horas normales: 0 a 20
                if((report.getInitDate().getHour() >= 0) && (report.getEndDate().getHour() < 20)) {
                    nextDayNightMinutesWorked += 7 - report.getInitDate().getHour();
                    normalMinutesWorked += report.getEndDate().getHour() - 7;

                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += nightMinutesWorked;
                        normalExtraMinutesWorked += normalMinutesWorked;
                    }
                }
                //Horas normales y nocturnas del mismo día: 7 a 23:59:59
                if((report.getInitDate().getHour() >= 7) && (report.getEndDate().getHour() >= 20 && report.getEndDate().getHour() < 24)) {
                    //??
                    dayNightMinutesWorked += report.getInitDate().getHour();

                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += nightMinutesWorked;
                        normalExtraMinutesWorked += normalMinutesWorked;
                    }
                }
                //Caso excepcional: horas nocturnas de madrugada AND horas normales AND horas nocturnas
                if(report.getInitDate().getHour() >= 0 && report.getEndDate().getHour() < 24) {

                }


            }
            nightMinutesWorked += nightExtraMinutesWorked + nextDayNightMinutesWorked;
            totalWorkedHours += workedMinutesInTheReport;
        }
        System.out.println("Total de horas trabajadas en una semana: "+ totalWorkedHours);

        return new WorkedHours(
                totalWorkedHours,
                normalMinutesWorked,
                nightMinutesWorked,
                sundayMinutesWorked,
                normalExtraMinutesWorked,
                nightExtraMinutesWorked,
                sundayExtraMinutesWorked
        );
    }

    /*if(     //1. Horas nocturnas madrugada: 24-7
            ((report.getInitDate().getHour() >= 24 && report.getInitDate().getHour() < 7) &&
            (report.getEndDate().getHour() >= 24 && report.getEndDate().getHour() < 7))
            &&
            //2. Horas normales: 7-7:59:59
            ((report.getInitDate().getHour() >= 7 && report.getInitDate().getHour() < 20) &&
            (report.getEndDate().getHour() >= 7 && report.getEndDate().getHour() < 20))
            &&
            //3. Horas nocturnas del mismo día: 20-24
            ((report.getInitDate().getHour() >= 20 && report.getInitDate().getHour() < 24) &&
            (report.getEndDate().getHour() >= 20 && report.getEndDate().getHour() < 24))
            ) {
        System.out.println("hello world");

    }*/










        /*for(Report report : reports) {
            ////how to get number of hours between two Java.time.LocalTime instances
            //Get the hours between init and end
            workedMinutesInTheReport = ChronoUnit.HOURS.between(report.getInitDate(), report.getEndDate());

            System.out.println(workedMinutesInTheReport);
            //Comparacion DIAS ENTRE LUNES Y SABADO
            if (report.getInitDate().getDayOfWeek().compareTo(DayOfWeek.MONDAY) >= 0 && report.getInitDate().getDayOfWeek().compareTo(DayOfWeek.SATURDAY) <= 0) {
                //.CompareTo:
                //The method returns 0 if the string is equal to the other string. A value less than 0 is returned if
                // the string is less than
                // the other string (less characters) and a value greater than 0 if
                // the string is greater than the other string (more characters).

                //Comparacion de HORAS ENTRE 0 y 7
                if ((INIT_OF_DAY <= report.getInitDate().getHour() && report.getEndDate().getHour() <= INIT_NORMAL_SHIFT_HOUR) &&
                        (INIT_OF_DAY <= report.getEndDate().getHour() && report.getEndDate().getHour() <= INIT_NORMAL_SHIFT_HOUR)) {
                    nightMinutesWorked += workedMinutesInTheReport;
                    if(totalWorkedHours > MAX_WORKED_HOURS_PER_WEEK){
                        nightExtraMinutesWorked += workedMinutesInTheReport;
                    }

                    //Combinacion de horas de trabajo entre nocturas y normales (0 a 7) 0 < initDateTime < 7  & 7 < endDateTime  < 20
                } else if ((INIT_OF_DAY <= report.getInitDate().getHour() && report.getInitDate().getHour() <= INIT_NORMAL_SHIFT_HOUR) &&
                        (INIT_NORMAL_SHIFT_HOUR <= report.getEndDate().getHour() && report.getEndDate().getHour() <= END_NORMAL_SHIFT_HOUR)) {
                    nightMinutesWorked += INIT_NORMAL_SHIFT_HOUR - (long) report.getInitDate().getHour();
                    normalMinutesWorked += (long) report.getEndDate().getHour() - INIT_NORMAL_SHIFT_HOUR;

                    if(totalWorkedHours > MAX_WORKED_HOURS_PER_WEEK){
                        nightExtraMinutesWorked += INIT_NORMAL_SHIFT_HOUR - (long) report.getInitDate().getHour();
                        normalExtraMinutesWorked += (long) report.getEndDate().getHour() - INIT_NORMAL_SHIFT_HOUR;
                    }

                    //Comparacion de HORAS ENTRE 7 Y 20 7 < initDateTime & endDateTime < 20
                } else if ((INIT_NORMAL_SHIFT_HOUR <= report.getInitDate().getHour() && report.getInitDate().getHour() <= END_NORMAL_SHIFT_HOUR) &&
                        (INIT_NORMAL_SHIFT_HOUR <= report.getEndDate().getHour() && report.getEndDate().getHour() <= END_NORMAL_SHIFT_HOUR)) {
                    normalMinutesWorked += workedMinutesInTheReport;

                    if(totalWorkedHours > MAX_WORKED_HOURS_PER_WEEK){
                        normalExtraMinutesWorked += workedMinutesInTheReport;
                    }

                    //Combinacion de horas de trabajo entre normales y nocturnas (20 a 23) 7 < initDateTime < 20  & 20 < endDateTime  < 24
                } else if ((INIT_NORMAL_SHIFT_HOUR <= report.getInitDate().getHour() && report.getInitDate().getHour() <= END_NORMAL_SHIFT_HOUR) &&
                        (END_NORMAL_SHIFT_HOUR <= report.getEndDate().getHour() && report.getEndDate().getHour() <= END_OF_DAY)) {
                    nightMinutesWorked += (long) report.getEndDate().getHour() - END_NORMAL_SHIFT_HOUR;
                    normalMinutesWorked += END_NORMAL_SHIFT_HOUR - (long) report.getInitDate().getHour();

                    if(totalWorkedHours > MAX_WORKED_HOURS_PER_WEEK){
                        nightExtraMinutesWorked += (long) report.getEndDate().getHour() - END_NORMAL_SHIFT_HOUR;
                        normalExtraMinutesWorked += END_NORMAL_SHIFT_HOUR - (long) report.getInitDate().getHour();
                    }

                    //Comparacion de HORAS ENTRE 20  Y 24 20 < initDateTime & endDateTime< 24
                } else if ((END_NORMAL_SHIFT_HOUR <= report.getInitDate().getHour() && report.getInitDate().getHour() <= END_OF_DAY) &&
                        (END_NORMAL_SHIFT_HOUR <= report.getEndDate().getHour() && report.getEndDate().getHour() <= END_OF_DAY)) {
                    nightMinutesWorked += workedMinutesInTheReport;

                    if (totalWorkedHours > MAX_WORKED_HOURS_PER_WEEK) {
                        nightExtraMinutesWorked += workedMinutesInTheReport;
                    }

                }
                //Horas dominicales
            } else if (report.getInitDate().getDayOfWeek().compareTo(DayOfWeek.SUNDAY) == 0) {
                sundayMinutesWorked += workedMinutesInTheReport;
                if (totalWorkedHours > MAX_WORKED_HOURS_PER_WEEK) {
                    sundayExtraMinutesWorked += sundayMinutesWorked;
                }

            }

            totalWorkedHours += workedMinutesInTheReport;
        }*/



    public TechnicianId getId() {
        return id;
    }

    public TechnicianName getName() {
        return name;
    }

    public TechnicianLastName getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "id=" + id +
                ", name=" + name +
                ", lastName=" + lastName +
                '}';
    }

}
