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
import java.util.ArrayList;
import java.util.HashMap;
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
        long accumulatorTotalWorked = 0L;

        long remainBetweenNormalAndExtra;
        long cutValue;
        //Remove:
        List<Long> hours;

        long normalMinutesWorked = 0L;
        long normalExtraMinutesWorked = 0L;
        long nightMinutesWorked = 0L;
        long nightExtraMinutesWorked = 0L;

        long sundayMinutesWorked = 0L;
        long sundayExtraMinutesWorked = 0L;

        HashMap<String, Long> calculatedHoursHash = new HashMap<>();

        for(Report report : reports) {
            calculatedHoursHash = this.hoursCalculated(report.getInitDate().getHour(),report.getEndDate().getHour());
            workedMinutesInTheReport = ChronoUnit.HOURS.between(report.getInitDate(), report.getEndDate());

            //Domingos:
            //CREAR MÉTODO:
            if (report.getInitDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
                if (accumulatorTotalWorked <= 48) {
                    if(accumulatorTotalWorked + workedMinutesInTheReport > 48) {
                        remainBetweenNormalAndExtra = 48-accumulatorTotalWorked;
                        //Boundary between sunday hours and overtime
                        cutValue = report.getInitDate().getHour() + remainBetweenNormalAndExtra;
                        sundayMinutesWorked += cutValue - report.getInitDate().getHour();
                        //Extra hours after boundary:
                        sundayExtraMinutesWorked += report.getEndDate().getHour() - cutValue;
                    } else {
                        //Extra hours
                        sundayMinutesWorked += workedMinutesInTheReport;
                    }



                } else {
                    sundayExtraMinutesWorked += sundayMinutesWorked;
                }
            }
            //Lunes a sábados:
            else {
                if (accumulatorTotalWorked <= 48) {
                    if(accumulatorTotalWorked + workedMinutesInTheReport > 48) {

                        remainBetweenNormalAndExtra = 48-accumulatorTotalWorked;
                        //Boundary between normal hours and overtime
                        cutValue = report.getInitDate().getHour() + remainBetweenNormalAndExtra;


                        calculatedHoursHash = this.hoursCalculated(report.getInitDate().getHour(),cutValue);
                        normalMinutesWorked += calculatedHoursHash.get("normalMinutesWorked");
                        nightMinutesWorked += calculatedHoursHash.get("night0_7");
                        nightMinutesWorked += calculatedHoursHash.get("night20_24");

                        //Extra hours after boundary:
                        //CREAR MÉTODO:
                        //11 hasta end => segunda parte (horas extra)
                        calculatedHoursHash = this.hoursCalculated(cutValue, report.getEndDate().getHour());
                        normalExtraMinutesWorked += calculatedHoursHash.get("normalMinutesWorked");
                        nightExtraMinutesWorked += calculatedHoursHash.get("night0_7");
                        nightExtraMinutesWorked += calculatedHoursHash.get("night20_24");
                    } else {
                        normalMinutesWorked += calculatedHoursHash.get("normalMinutesWorked");
                        nightMinutesWorked += calculatedHoursHash.get("night0_7");
                        nightMinutesWorked += calculatedHoursHash.get("night20_24");
                    }
                } else {
                    //Extra hours:
                    normalExtraMinutesWorked += calculatedHoursHash.get("normalMinutesWorked");
                    nightExtraMinutesWorked += calculatedHoursHash.get("night0_7");
                    nightExtraMinutesWorked += calculatedHoursHash.get("night20_24");
                }
            }
            accumulatorTotalWorked += workedMinutesInTheReport;

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

    public void calculateSundayHours(long initDate, long endDate, long cutValue) {



    }


    public List<Long> calculateMondayToSaturdayHours(HashMap<String,Long> dictHours) {
        List<Long> cars = new ArrayList<>();
        cars.add(dictHours.get("normalMinutesWorked"));
        cars.add(dictHours.get("night0_7"));
        cars.add(dictHours.get("night20_24"));

        return cars;
    }

    public HashMap<String,Long> hoursCalculated(long initHour, long endHour) {
        HashMap<String, Long> hoursCalculated = new HashMap<>();
        hoursCalculated.put("night0_7", 0L);
        hoursCalculated.put("night20_24", 0L);
        hoursCalculated.put("normalMinutesWorked", 0L);
        //0-7
        if(initHour < 7 && endHour >= 7) {
            hoursCalculated.replace("night0_7", 7 - initHour);
        } else if(initHour < 7 && endHour < 7) {
            hoursCalculated.replace("night0_7", endHour - initHour);
        }
        //0-20
        if (endHour > 7) {
            if(initHour <= 7 && endHour >= 20) {
                hoursCalculated.replace("normalMinutesWorked", 13L);
            } else if(initHour < 7 && initHour <= 20) {
                hoursCalculated.replace("normalMinutesWorked", endHour - 7L);
            } else if(initHour >= 7 && endHour > 20) {
                hoursCalculated.replace("normalMinutesWorked", 20L - initHour);
            } else {
                hoursCalculated.replace("normalMinutesWorked", endHour - initHour);
            }
        }
        //20-24
        if(endHour > 20) {
            hoursCalculated.replace("night20_24", endHour - 20);
        }
        return hoursCalculated;

    }
}
