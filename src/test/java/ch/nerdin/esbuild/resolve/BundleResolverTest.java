package ch.nerdin.esbuild.resolve;

import ch.nerdin.esbuild.Bundler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BundleResolverTest extends BundleTester {

    @BeforeAll
    public static void cleanUp() throws IOException {
        cleanUpDefault();
    }

    @Test
    public void resolve() throws IOException {
        // when
        final Path resolve = new BundledResolver(null).resolve(Bundler.getDefaultVersion());

        // then
        assertTrue(resolve.toFile().exists());
    }
}
