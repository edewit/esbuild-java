package io.mvnpm.esbuild.resolve;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.mvnpm.esbuild.Bundler;
import io.mvnpm.esbuild.EsBuildFilenameMapper;
import io.mvnpm.process.resolve.BundledResolver;
import io.mvnpm.process.resolve.Resolver;

public class BundleResolverTest extends BundleTester {

    public static final Resolver THROWING_RESOLVER = version -> {
        throw new RuntimeException("Should not call this");
    };

    @BeforeAll
    public static void cleanUp() throws IOException {
        cleanUpDefault();
    }

    @Test
    public void resolve() throws IOException {
        // when
        final Path resolve = new BundledResolver(THROWING_RESOLVER, new EsBuildFilenameMapper())
                .resolve(Bundler.ESBUILD_EMBEDDED_VERSION);

        assertFalse(Bundler.ESBUILD_EMBEDDED_VERSION.contains("mvnpm"));

        // then
        assertTrue(resolve.toFile().exists());
    }
}
