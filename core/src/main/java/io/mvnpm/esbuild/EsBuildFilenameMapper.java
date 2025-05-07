package io.mvnpm.esbuild;

import java.nio.file.Files;
import java.nio.file.Path;

import io.mvnpm.process.FilenameMapper;

public class EsBuildFilenameMapper implements FilenameMapper {
    public static final String MVNPM_TGZ_PATH_TEMPLATE = "esbuild-%1$s-%2$s.tgz";
    public static final String ESBUILD_TGZ_PATH_TEMPLATE = "%1$s-%2$s.tgz";
    public static final String MVNPM_URL_TEMPLATE = "https://github.com/mvnpm/esbuild/releases/download/v%1$s";
    public static final String ESBUILD_URL_TEMPLATE = "https://registry.npmjs.org/@esbuild/%1$s/-/";
    private static final String LEGACY_PATH = "package/esbuild";
    private static final String PATH = "package/bin/esbuild";

    @Override
    public String downloadUrl(String version, String classifier) {
        final String tgz = tarFileName(version, classifier);
        if (version.contains("mvnpm")) {
            return MVNPM_URL_TEMPLATE.formatted(version.substring(version.lastIndexOf("-") + 1)) + "/" + tgz;
        }
        return ESBUILD_URL_TEMPLATE.formatted(classifier) + tgz;
    }

    @Override
    public String tarFileName(String version, String classifier) {
        if (version.contains("mvnpm")) {
            return MVNPM_TGZ_PATH_TEMPLATE.formatted(classifier, version);
        } else {
            return ESBUILD_TGZ_PATH_TEMPLATE.formatted(classifier, version);
        }
    }

    private static boolean isWindows() {
        final String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("win");
    }

    static boolean isLegacyBundle(Path location) {
        return !Files.isDirectory(location.resolve("package/bin"));
    }

    @Override
    public String executable(Path bundleDir) {
        String path;
        if (isLegacyBundle(bundleDir)) {
            path = LEGACY_PATH;
        } else {
            path = PATH;
        }
        return bundleDir.resolve(isWindows() ? path + ".exe" : path).toString();
    }
}
