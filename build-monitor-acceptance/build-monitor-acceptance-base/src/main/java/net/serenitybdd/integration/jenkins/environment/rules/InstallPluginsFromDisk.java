package net.serenitybdd.integration.jenkins.environment.rules;

import com.google.common.base.Joiner;
import net.serenitybdd.integration.jenkins.JenkinsInstance;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;

public class InstallPluginsFromDisk implements ApplicativeTestRule<JenkinsInstance> {
    private static final Logger Log = LoggerFactory.getLogger(InstallPluginsFromDisk.class);

    private final List<Path> pluginsToInstall;

    public InstallPluginsFromDisk(Path... pluginsToInstall) {
        this.pluginsToInstall = asList(pluginsToInstall);
    }

    @Override
    public TestRule applyTo(final JenkinsInstance jenkins) {
        return new TestWatcher() {
            @Override
            protected void starting(Description description) {
                Path pluginsDir = jenkins.home().resolve("plugins");
                String plugins  = Joiner.on(", ").join(pluginsToInstall);

                Log.info("Installing {} into {}", plugins, pluginsDir);

                try {
                    Files.createDirectories(jenkins.home().resolve("plugins"));

                    for (Path plugin : pluginsToInstall) {
                        Files.copy(existing(plugin), pluginsDir.resolve(plugin.getFileName()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(String.format("Couldn't install '%s' under '%s'", plugins, pluginsDir.toAbsolutePath()));
                }
            }

            private Path existing(Path plugin) {
                checkArgument(Files.exists(plugin), String.format("Plugin file '%s' doesn't exist and couldn't be installed.", plugin));

                return plugin;
            }
        };
    }
}
