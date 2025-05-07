package io.mvnpm.esbuild;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import io.mvnpm.esbuild.model.EntryPoint;
import io.mvnpm.process.model.WatchExecuteResult;
import io.mvnpm.process.model.WatchStartResult;

public class Watch implements Closeable {

    private final WatchStartResult.WatchProcess process;
    private final Path workDir;

    private final Path dist;

    private final WatchExecuteResult firstBuildResult;

    public Watch(WatchStartResult.WatchProcess process, Path workDir, Path dist, WatchExecuteResult firstBuildResult) {
        this.process = process;
        this.workDir = workDir;
        this.dist = dist;
        this.firstBuildResult = firstBuildResult;
    }

    public void updateEntries(List<EntryPoint> entries) throws IOException {
        entries.forEach(entry -> entry.process(workDir));
    }

    @Override
    public void close() throws IOException {
        process.close();
    }

    public Path workDir() {
        return workDir;
    }

    public WatchExecuteResult firstBuildResult() {
        return firstBuildResult;
    }

    public boolean isAlive() {
        return process.isAlive();
    }

    public Path dist() {
        return dist;
    }

}
