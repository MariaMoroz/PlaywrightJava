package constants;

public class JenkinsConstants {
    public static final String loginUserName = "admin";
    public static final String loginPassword = "22e5c68b03e4419bbe4f6a3617274adc";
    public static final String projectName = "Project1";
    public static final String expectedTooltipHelpText = "This determines when, if ever, build records for this project should be discarded. Build records include the console output, archived artifacts, and any other metadata related to a particular build.\n" +
            "\n" +
            "Keeping fewer builds means less disk space will be used in the Build Record Root Directory , which is specified on the Configure System screen.\n" +
            "\n" +
            "Jenkins offers two options for determining when builds should be discarded:\n" +
            "\n" +
            "Build age: discard builds when they reach a certain age; for example, seven days old.\n" +
            "Build count: discard the oldest build when a certain number of builds already exist.\n" +
            "These two options can be active at the same time, so you can keep builds for 14 days, but only up to a limit of 50 builds, for example. If either limit is exceeded, then any builds beyond that limit will be discarded.\n" +
            "\n" +
            "You can also ensure that important builds are kept forever, regardless of the setting here — click the Keep this build forever button on the build page.\n" +
            "The last stable and last successful build are also excluded from these rules.\n" +
            "\n" +
            "In the Advanced section, the same options can be specified, but specifically for build artifacts . If enabled, build artifacts will be discarded for any builds which exceed the defined limits. The builds themselves will still be kept; only the associated artifacts, if any, will be deleted.\n" +
            "\n" +
            "For example, if a project builds some software and produces a large installer, which is archived, you may wish to always keep the console log and information about which source control commit was built, while for disk space reasons, you may want to keep only the last three installers that were built.\n" +
            "This can make sense for projects where you can easily recreate the same artifacts later by building the same source control commit again.\n" +
            "\n" +
            "Note that Jenkins does not discard items immediately when this configuration is updated, or as soon as any of the configured values are exceeded; these rules are evaluated each time a build of this project completes.";


}
