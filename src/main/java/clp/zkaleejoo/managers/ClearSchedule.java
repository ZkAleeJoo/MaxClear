package clp.zkaleejoo.managers;

public record ClearSchedule(
        boolean clearEnabled,
        boolean warningEnabled,
        long clearDelayTicks,
        long warningDelayTicks,
        String warningReason) {

    private static final long TICKS_PER_SECOND = 20L;

    public static ClearSchedule from(boolean autoClearEnabled, int intervalSeconds, boolean warningEnabled,
            int warningSecondsBefore) {
        if (!autoClearEnabled) {
            return new ClearSchedule(false, false, 0L, 0L, null);
        }

        if (intervalSeconds <= 0) {
            return new ClearSchedule(false, false, 0L, 0L,
                    "auto-clear.interval must be greater than 0 seconds.");
        }

        long intervalTicks = intervalSeconds * TICKS_PER_SECOND;
        if (!warningEnabled) {
            return new ClearSchedule(true, false, intervalTicks, 0L, null);
        }

        if (warningSecondsBefore <= 0 || warningSecondsBefore >= intervalSeconds) {
            return new ClearSchedule(true, false, intervalTicks, 0L,
                    "messages.warning.seconds-before must be greater than 0 and lower than auto-clear.interval.");
        }

        long warningDelayTicks = (intervalSeconds - warningSecondsBefore) * TICKS_PER_SECOND;
        return new ClearSchedule(true, true, intervalTicks, warningDelayTicks, null);
    }
}
