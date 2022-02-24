package com.handyman.handymanbe.domain.workedHours;

import java.util.Objects;

public class WorkedHours {
    private final Long totalHours;
    private final Long normalHours;
    private final Long nightHours;
    private final Long sundayHours;
    private final Long extraNormalHours;
    private final Long extraNightHours;
    private final Long extraSundayHours;

    public WorkedHours(Long totalHours, Long normalHours, Long nightHours, Long sundayHours, Long extraNormalHours, Long extraNightHours, Long extraSundayHours) {
        Objects.requireNonNull(totalHours, "Total hours can not be null");
        Objects.requireNonNull(normalHours, "NormalHours can not be null");
        Objects.requireNonNull(nightHours, "NightHours can not be null");
        Objects.requireNonNull(sundayHours, "SundayHours can not be null");
        Objects.requireNonNull(extraNormalHours, "ExtraNormalHours can not be null");
        Objects.requireNonNull(extraNightHours, "ExtraNightHours can not be null");
        Objects.requireNonNull(extraSundayHours, "extraSundayHours can not be null");

        this.totalHours = totalHours;
        this.normalHours = normalHours;
        this.nightHours = nightHours;
        this.sundayHours = sundayHours;
        this.extraNormalHours = extraNormalHours;
        this.extraNightHours = extraNightHours;
        this.extraSundayHours = extraSundayHours;
    }

    public Long getTotalHours() {
        return totalHours;
    }
    public Long getNormalHours() {
        return normalHours;
    }

    public Long getNightHours() {
        return nightHours;
    }

    public Long getSundayHours() {
        return sundayHours;
    }

    public Long getExtraNormalHours() {
        return extraNormalHours;
    }

    public Long getExtraNightHours() {
        return extraNightHours;
    }

    public Long getExtraSundayHours() {
        return extraSundayHours;
    }

}
