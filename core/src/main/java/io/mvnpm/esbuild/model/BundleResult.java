package io.mvnpm.esbuild.model;

import java.nio.file.Path;

import io.mvnpm.process.model.ExecuteResult;

public record BundleResult(Path dist, Path workDir, ExecuteResult result) {
}
