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

        /*long dayNightMinutesWorked = 0L;
        long nextDayNightMinutesWorked = 0L;*/

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
                //Horas normales: 7-20h (done)
                if((report.getInitDate().getHour() >= 7 && report.getInitDate().getHour() < 20)
                        && (report.getEndDate().getHour() >= 7 && report.getEndDate().getHour() < 20)) {
                    normalMinutesWorked += workedMinutesInTheReport;
                    //Horas normales extra: +48h
                    if (totalWorkedHours > 48) {
                        normalExtraMinutesWorked += workedMinutesInTheReport;
                    }
                }
                //Horas nocturnas de 0 a 6:59:59 (done)
                else if((report.getInitDate().getHour() >= 0 && report.getInitDate().getHour() < 7)
                        && (report.getEndDate().getHour() >= 0 && report.getEndDate().getHour() < 7)) {
                    nightMinutesWorked += workedMinutesInTheReport;
                    //Horas nocturnas extra madrugada: +48h
                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += workedMinutesInTheReport;
                    }

                }
                //Horas nocturnas de 20 a 23:59:59: (done)
                else if((report.getInitDate().getHour() >= 20 && report.getInitDate().getHour() < 24)
                        && (report.getEndDate().getHour() >= 20 && report.getEndDate().getHour() < 24)) {
                    nightMinutesWorked += workedMinutesInTheReport;
                    //Horas nocturnas extra madrugada: +48h
                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += workedMinutesInTheReport;
                    }
                }
                //Horas entre nocturnas de madrugada y horas normales: 0 a 20 (done)
                else if((report.getInitDate().getHour() >= 0 && report.getInitDate().getHour() <= 7) &&
                        (report.getEndDate().getHour() > 7 && report.getEndDate().getHour() < 20)) {
                    nightMinutesWorked += 7 - (long) report.getInitDate().getHour();
                    normalMinutesWorked += (long) report.getEndDate().getHour() - 7;

                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += nightMinutesWorked;
                        normalExtraMinutesWorked += normalMinutesWorked;
                    }
                }
                //Horas normales y nocturnas del mismo día: 7 a 23:59:59 (done)
                else if((report.getInitDate().getHour() >= 7 && report.getInitDate().getHour() < 20) &&
                        (report.getEndDate().getHour() >= 20 && report.getEndDate().getHour() < 24)) {
                    nightMinutesWorked += (long) report.getEndDate().getHour() - 20;
                    normalExtraMinutesWorked += 20 - (long) report.getInitDate().getHour();

                    if (totalWorkedHours > 48) {
                        nightExtraMinutesWorked += (long) report.getEndDate().getHour() - 20;
                        normalExtraMinutesWorked += 20 - (long) report.getInitDate().getHour();
                    }
                }
                //Caso excepcional: horas nocturnas de madrugada AND horas normales AND horas nocturnas (pendiente)
                //¿Falta algún otro caso?
                else {
                    if(report.getInitDate().getHour() < 7 && report.getEndDate().getHour() >= 7) {
                        nightMinutesWorked += 7 - report.getInitDate().getHour();
                        if (totalWorkedHours > 48) {

                        }

                    } if(report.getInitDate().getHour() >= 7 && report.getInitDate().getHour() < 20) {
                        normalMinutesWorked += (long) report.getInitDate().getHour() - 7;
                        if (totalWorkedHours > 48) {

                        }

                    } if(report.getInitDate().getHour() >= 20 && report.getInitDate().getHour() < 24) {
                        if (totalWorkedHours > 48) {

                        }

                    }


                }
            }
            //nightMinutesWorked += nightExtraMinutesWorked + nextDayNightMinutesWorked;
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
