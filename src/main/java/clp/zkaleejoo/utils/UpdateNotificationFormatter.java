package clp.zkaleejoo.utils;

import java.util.List;

public final class UpdateNotificationFormatter {

    private UpdateNotificationFormatter() {
    }

    public static List<String> format(String prefix, String updateAvailable, String updateCurrent,
            String updateDownload, String currentVersion, String latestVersion, String downloadUrl) {
        if (latestVersion == null || latestVersion.isBlank()
                || currentVersion == null || currentVersion.equalsIgnoreCase(latestVersion)) {
            return List.of();
        }

        String resolvedLatest = latestVersion.trim();
        String resolvedCurrent = currentVersion == null ? "" : currentVersion;

        return List.of(
                " ",
                safe(prefix) + safe(updateAvailable).replace("{version}", resolvedLatest),
                safe(updateCurrent).replace("{version}", resolvedCurrent),
                safe(updateDownload),
                safe(downloadUrl),
                " ");
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
