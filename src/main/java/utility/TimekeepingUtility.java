package utility;

import config.Config;

import java.sql.Time;

public class TimekeepingUtility {
    public static float getHourShift1Worker(Time time_in, Time time_out) {
        float shift1 = 0.0f;

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) < 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) > 0) {
            return 4.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) > 0 ||
                time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) < 0) {
            return 0.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) >= 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) <= 0) {
            shift1 = (time_out.getTime() - time_in.getTime()) / 3600000.0f;
        }
        
        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) < 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) <= 0) {
            shift1 = (time_out.getTime() - Time.valueOf(Config.WORKER_START_SHIFT1).getTime()) / 3600000.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT1)) >= 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT1)) > 0) {
            shift1 = (Time.valueOf(Config.WORKER_END_SHIFT1).getTime() - time_in.getTime()) / 3600000.0f;
        }

        return (float) (Math.round(shift1 * 100.0) / 100.0);
    }

    public static float getHourShift2Worker(Time time_in, Time time_out) {
        float shift2 = 0.0f;

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) < 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) > 0) {
            return 4.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) > 0 ||
                time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) < 0) {
            return 0.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) >= 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) <= 0) {
            shift2 = (time_out.getTime() - time_in.getTime()) / 3600000.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) < 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) <= 0) {
            shift2 = (time_out.getTime() - Time.valueOf(Config.WORKER_START_SHIFT2).getTime()) / 3600000.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT2)) >= 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT2)) > 0) {
            shift2 = (Time.valueOf(Config.WORKER_END_SHIFT2).getTime() - time_in.getTime()) / 3600000.0f;
        }

        return (float) (Math.round(shift2 * 100.0) / 100.0);
    }

    public static float getHourShift3Worker(Time time_in, Time time_out) {
        float shift3 = 0.0f;
        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT3)) < 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT3)) > 0) {
            return 4.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_END_SHIFT3)) > 0 ||
                time_out.compareTo(Time.valueOf(Config.WORKER_START_SHIFT3)) < 0) {
            return 0.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT3)) >= 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT3)) <= 0) {
            shift3 = (time_out.getTime() - time_in.getTime()) / 3600000.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT3)) < 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT3)) <= 0) {
            shift3 = (time_out.getTime() - Time.valueOf(Config.WORKER_START_SHIFT3).getTime()) / 3600000.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.WORKER_START_SHIFT3)) >= 0 &&
                time_out.compareTo(Time.valueOf(Config.WORKER_END_SHIFT3)) > 0) {
            shift3 = (Time.valueOf(Config.WORKER_END_SHIFT3).getTime() - time_in.getTime()) / 3600000.0f;
        }

        return (float) (Math.round(shift3 * 100.0) / 100.0);
    }

    public static boolean isMorningOfficer(Time time_in, Time time_out) {
        if (time_in.compareTo(Time.valueOf(Config.OFFICER_END_MORNING)) > 0 ||
                time_out.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) < 0) {
            return false;
        }
        return true;
    }

    public static boolean isAfternoonOfficer(Time time_in, Time time_out) {
        if (time_in.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) > 0 ||
                time_out.compareTo(Time.valueOf(Config.OFFICER_START_AFTERNOON)) < 0) {
            return false;
        }
        return true;
    }

    public static float getHourEarlyOfficer(Time time_out) {
        if (time_out.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) > 0 &&
                time_out.compareTo(Time.valueOf(Config.OFFICER_END_MORNING)) < 0) {
            return (Time.valueOf(Config.OFFICER_END_MORNING).getTime() - time_out.getTime() ) / 3600000.0f;
        }

        if (time_out.compareTo(Time.valueOf(Config.OFFICER_START_AFTERNOON)) > 0 &&
                time_out.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) < 0) {
           return (Time.valueOf(Config.OFFICER_END_AFTERNOON).getTime() - time_out.getTime() ) / 3600000.0f;
        }

        return 0.0f;
    }

    public static float getHourLateOfficer(Time time_in) {
        if (time_in.compareTo(Time.valueOf(Config.OFFICER_START_MORNING)) > 0 &&
                time_in.compareTo(Time.valueOf(Config.OFFICER_END_MORNING)) < 0) {
            return (time_in.getTime() - Time.valueOf(Config.OFFICER_START_MORNING).getTime()) / 3600000.0f;
        }

        if (time_in.compareTo(Time.valueOf(Config.OFFICER_START_AFTERNOON)) > 0 &&
                time_in.compareTo(Time.valueOf(Config.OFFICER_END_AFTERNOON)) < 0) {
            return (time_in.getTime() - Time.valueOf(Config.OFFICER_START_AFTERNOON).getTime()) / 3600000.0f;
        }

        return 0.0f;
    }

    public static float getHourOvertimeOfficer(Time time_out)
    {
        if (time_out.compareTo(Time.valueOf(Config.OFFICER_START_OVERTIME)) > 0) {
            return (time_out.getTime() - Time.valueOf(Config.OFFICER_START_OVERTIME).getTime()) / 3600000.0f;
        }
        return 0.0f;
    }
}
