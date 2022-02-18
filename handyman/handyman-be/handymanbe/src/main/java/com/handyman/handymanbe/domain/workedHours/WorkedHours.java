package com.handyman.handymanbe.domain.workedHours;

import java.util.Objects;

public class WorkedHours {
    private final long normalHours;
    private final long nightHours;
    private final long sundayHours;
    private final long extraNormalHours;
    private final long extraNightHours;
    private final long extraSundayHours;

    public WorkedHours(long normalHours, long nightHours, long sundayHours, long extraNormalHours, long extraNightHours, long extraSundayHours) {
        Objects.requireNonNull(normalHours, "NormalHours can not be null");
        Objects.requireNonNull(nightHours, "NightHours can not be null");
        Objects.requireNonNull(sundayHours, "SundayHours can not be null");
        Objects.requireNonNull(extraNormalHours, "ExtraNormalHours can not be null");
        Objects.requireNonNull(extraNightHours, "ExtraNightHours can not be null");
        Objects.requireNonNull(extraSundayHours, "extraSundayHours can not be null");

        this.normalHours = normalHours;
        this.nightHours = nightHours;
        this.sundayHours = sundayHours;
        this.extraNormalHours = extraNormalHours;
        this.extraNightHours = extraNightHours;
        this.extraSundayHours = extraSundayHours;
    }

    public long getNormalHours() {
        return normalHours;
    }

    public long getNightHours() {
        return nightHours;
    }

    public long getSundayHours() {
        return sundayHours;
    }

    public long getExtraNormalHours() {
        return extraNormalHours;
    }

    public long getExtraNightHours() {
        return extraNightHours;
    }

    public long getExtraSundayHours() {
        return extraSundayHours;
    }
}
