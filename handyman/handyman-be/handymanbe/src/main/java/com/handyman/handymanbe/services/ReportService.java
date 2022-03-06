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

    /*public HashMap<String,Long> hoursCalculatedNormalBoundary(long initHour, long cutValue) {
        HashMap<String, Long> hoursCalculated = new HashMap<>();
        long remainBoundary = cutValue - initHour;
        //0-7
        if(remainBoundary >= 0) {
            hoursCalculated.put("night0_7", 7 - initHour);
            remainBoundary -= hoursCalculated.get("night0_7");
        }
        //0-20
        if(remainBoundary >= 0) {
            hoursCalculated.put("normalMinutesWorked", remainBoundary);
            remainBoundary -= hoursCalculated.get("normalMinutesWorked");
        }
        //20-24
        if(remainBoundary >= 0) {
            hoursCalculated.put("night20_24", remainBoundary);
        }
        return hoursCalculated;

    }*/

    /*public HashMap<String,Long> hoursCalculatedExtraBoundary(long cutValue, long endHour) {
        HashMap<String, Long> hoursCalculated = new HashMap<>();
        //0-7
        if (cutValue < 7) {
            hoursCalculated.put("night0_7", cutValue - 7);
        }
        //0-20
        if (cutValue <= 7 && endHour >= 20) {
            hoursCalculated.put("normalMinutesWorked", 13L);
        } else if (cutValue < 7 && cutValue <= 20) {
            hoursCalculated.put("normalMinutesWorked", endHour - 7L);
        } else if (cutValue >= 7 && endHour > 20) {
            hoursCalculated.put("normalMinutesWorked", 20L - cutValue);
        } else {
            hoursCalculated.put("normalMinutesWorked", endHour - cutValue);
        }
        //20-24
        if (endHour > 20) {
            hoursCalculated.put("night20_24", endHour - 20);
        }
        return hoursCalculated;
    }*/
}


















        /*
             HashMap<String, Long> calculatedHoursHash = new HashMap<>();

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

                                    long workedMinutesInTheReport;

        if(workedMinutesInTheReport + accumulatorTotalWorked > 48) {
            long cutValue = 0L;
            remainBetweenNormalAndExtra = (48-accumulatorTotalWorked);
            cutValue = report.getInitDate().getHour() + remainBetweenNormalAndExtra; //11h donde pasa normales a extra
            calculatedHoursHash = this.hoursCalculated(report.getInitDate().getHour(),cutValue);
            normalMinutesWorked += calculatedHoursHash.containsKey("normalMinutesWorked") ? calculatedHoursHash.get("normalMinutesWorked") : 0L;

            nightMinutesWorked += calculatedHoursHash.get("night0_7");
            nightMinutesWorked += calculatedHoursHash.get("night20_24");
            calculatedHoursHash = this.hoursCalculated(report.getInitDate().getHour(),cutValue);

            normalExtraMinutesWorked = calculatedHoursHash.get("normalMinutesWorked");
            nightExtraMinutesWorked = calculatedHoursHash.get("night0_7") + calculatedHoursHash.get("night20_24");
                }
            }
        }
            }
                    accumulatorTotalWorked += workedMinutesInTheReport;



                    }


         */

