package com.handyman.handymanbe.services;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.workedHours.WorkedHours;
import com.handyman.handymanbe.repository.report.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private TechnicianService technicianService;

    public ReportRepository repository;
    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report createReport(Report report) {
        repository.create(report);
        return report;
    }

    public List<Report> listReports() {
        return repository.list();
    }

    public WorkedHours calculateHoursOfWorkForTechnician(Integer week, TechnicianId id){
        Technician technician = technicianService.getTechnician(id);
        List<Report> reports = repository.getAllFromTechnicianByWeek(week, id);
        WorkedHours workedHours = this.workedHoursCalculate(reports);
        return workedHours;
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
        long accumulatorTotalWorked = 0L;

        long night0_7 = 0L;
        long night20_24 = 0L;
        long extraNight0_7 = 0L;
        long extraNight20_24 = 0L;
        long remainBetweenNormalAndExtra = 0L;
        long remain2 = 0L;


        for(Report report : reports) {
            workedMinutesInTheReport = ChronoUnit.HOURS.between(report.getInitDate(), report.getEndDate());
            totalWorkedHours += workedMinutesInTheReport;



            //Domingos:
            if (report.getInitDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
                if (totalWorkedHours <= 48) {
                    sundayMinutesWorked += workedMinutesInTheReport;
                } else {
                    sundayExtraMinutesWorked += sundayMinutesWorked;
                }
            }
            //Lunes a sábados:
            else {

                //Horas normales: 7-20h (done)
                if((report.getInitDate().getHour() >= 7 && report.getInitDate().getHour() < 20)
                        && (report.getEndDate().getHour() >= 7 && report.getEndDate().getHour() < 20)) {
                    if (totalWorkedHours <= 48) {
                        normalMinutesWorked += workedMinutesInTheReport;
                    }
                    //Horas normales extra: +48h
                    else {
                        normalExtraMinutesWorked += workedMinutesInTheReport;
                    }
                }
                //Horas nocturnas de 0 a 6:59:59 (done)
                else if((report.getInitDate().getHour() >= 0 && report.getInitDate().getHour() < 7)
                        && (report.getEndDate().getHour() >= 0 && report.getEndDate().getHour() < 7)) {
                    if (totalWorkedHours <= 48) {
                        nightMinutesWorked += workedMinutesInTheReport;
                    }
                    //Horas nocturnas extra madrugada: +48h
                    else {
                        nightExtraMinutesWorked += workedMinutesInTheReport;
                    }

                }
                //Horas nocturnas de 20 a 23:59:59: (done)
                else if((report.getInitDate().getHour() >= 20 && report.getInitDate().getHour() < 24)
                        && (report.getEndDate().getHour() >= 20 && report.getEndDate().getHour() < 24)) {
                    if (totalWorkedHours <= 48) {
                        nightMinutesWorked += workedMinutesInTheReport;
                    }
                    //Horas nocturnas extra madrugada: +48h
                    else {
                        nightExtraMinutesWorked += workedMinutesInTheReport;
                    }
                }
                //Horas entre nocturnas de madrugada y horas normales: 0 a 20 (done)
                else if((report.getInitDate().getHour() >= 0 && report.getInitDate().getHour() <= 7) &&
                        (report.getEndDate().getHour() > 7 && report.getEndDate().getHour() < 20)) {
                    if (totalWorkedHours <= 48) {
                        normalMinutesWorked += ((long) report.getEndDate().getHour() - 7);
                        nightMinutesWorked += (7 - (long) report.getInitDate().getHour());
                    } else {
                        normalExtraMinutesWorked += ((long) report.getEndDate().getHour() - 7);
                        nightExtraMinutesWorked += (7 - (long) report.getInitDate().getHour());
                    }
                }
                //Horas normales y nocturnas del mismo día: 7 a 23:59:59 (done)
                else if((report.getInitDate().getHour() >= 7 && report.getInitDate().getHour() < 20) &&
                        (report.getEndDate().getHour() >= 20 && report.getEndDate().getHour() < 24)) {
                    if(totalWorkedHours <= 48) {
                        normalMinutesWorked += (20 - (long) report.getInitDate().getHour());
                        nightMinutesWorked += (24 - (long) report.getEndDate().getHour());

                    } else {
                        normalExtraMinutesWorked += (20 - (long) report.getInitDate().getHour());
                        nightExtraMinutesWorked += (24 - (long) report.getEndDate().getHour());
                    }
                }
                //Caso excepcional: horas nocturnas de madrugada AND horas normales AND horas nocturnas (done)
                else {
                    if (totalWorkedHours <= 48) {
                        //0-7h
                        night0_7 = (7 - (long) report.getInitDate().getHour());
                        //7-20h
                        normalMinutesWorked += 13;
                        //20-24h
                        night20_24 = ((long) report.getEndDate().getHour() - 20);
                        //Total:
                        nightMinutesWorked += night0_7 + night20_24;

                    } else {
                        // 1. ACUMULADO:    actualizar al final
                        // comparar con el reoporte > 48?
                        //acum + horastotales > 48 {}
                        //2. acum > 48?
                /*división de horas
                    cómo?
                        init-end
                            a.
                            init + (48-acum)
                                ejem: 3h + (48-40) = 11.
                                rango1:
                                    init hasta 11 => primera parte (horas normales)
                                rango2:
                                    11 hasta end => segunda parte (horas extra)
                 */
                        if(workedMinutesInTheReport + accumulatorTotalWorked > 48) {
                            //remainBetweenNormalAndExtra = report.getInitDate().getHour() + (48-accumulatorTotalWorked);
                            remainBetweenNormalAndExtra = (48-accumulatorTotalWorked);
                            night0_7 = ((7 + report.getInitDate().getHour()) - remainBetweenNormalAndExtra);
                            if(night0_7 + report.getInitDate().getHour() > 7) {
                                normalMinutesWorked += remainBetweenNormalAndExtra - night0_7;
                            }
                            nightMinutesWorked += night0_7 + night20_24;



                        }
                        //0-7h
                        extraNight0_7 = (7 - (long) report.getInitDate().getHour());
                        //7-20h
                        normalExtraMinutesWorked += 13;
                        //20-24h
                        extraNight20_24 = ((long) report.getEndDate().getHour() - 24);
                        //Total:
                        nightExtraMinutesWorked += extraNight0_7 + extraNight20_24;
                    }
                }
            }
            accumulatorTotalWorked += workedMinutesInTheReport;


            //Get minutes?:
            //report.getInitDate().getMinute();
        }




        return new WorkedHours(
                accumulatorTotalWorked,
                normalMinutesWorked,
                nightMinutesWorked,
                sundayMinutesWorked,
                normalExtraMinutesWorked,
                nightExtraMinutesWorked,
                sundayExtraMinutesWorked
        );
    }



}
