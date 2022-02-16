package com.handyman.handymanbe.domain.workedHours;

public class WorkedHours {
    private final Integer normalHours;
    private final Integer nightHours;
    private final Integer sundayHours;
    private final Integer extraNormalHours;
    private final Integer extraNightHours;
    private final Integer extraSundayHours;

    public WorkedHours(Integer normalHours, Integer nightHours, Integer sundayHours, Integer extraNormalHours, Integer extraNightHours, Integer extraSundayHours) {
        this.normalHours = normalHours;
        this.nightHours = nightHours;
        this.sundayHours = sundayHours;
        this.extraNormalHours = extraNormalHours;
        this.extraNightHours = extraNightHours;
        this.extraSundayHours = extraSundayHours;
    }

    public Integer getNormalHours() {
        return normalHours;
    }

    public Integer getNightHours() {
        return nightHours;
    }

    public Integer getSundayHours() {
        return sundayHours;
    }

    public Integer getExtraNormalHours() {
        return extraNormalHours;
    }

    public Integer getExtraNightHours() {
        return extraNightHours;
    }

    public Integer getExtraSundayHours() {
        return extraSundayHours;
    }
}
