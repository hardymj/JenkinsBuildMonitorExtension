package com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.features;

import com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.JobView;
import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.syntacticsugar.Sugar.*;
import static com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.syntacticsugar.TimeMachine.currentTime;
import static hudson.model.Result.SUCCESS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jenkins.class})
public class KnowsLastCompletedBuildDetailsTest {
    private JobView view;

    @Mock
    private Jenkins jenkins;

    @Before
    public void setup() {
        PowerMockito.mockStatic(Jenkins.class);
        PowerMockito.when(Jenkins.getInstance()).thenReturn(jenkins);
    }

    @Test
    public void should_know_current_build_number() {
        view = a(jobView().which(new KnowsLastCompletedBuildDetails()).of(
                a(job().whereTheLast(build().hasNumber(5)))));

        assertThat(lastCompletedBuildOf(view).name(), is("#5"));
    }

    @Test
    public void should_use_build_name_if_its_known() {
        view = a(jobView().which(new KnowsLastCompletedBuildDetails()).of(
                a(job().whereTheLast(build().hasName("1.3.4+build.15")))));

        assertThat(lastCompletedBuildOf(view).name(), is("1.3.4+build.15"));
    }

    @Test
    public void should_know_the_url_of_the_last_build() {
        view = a(jobView().which(new KnowsLastCompletedBuildDetails()).of(
                a(job().whereTheLast(build().hasNumber(22))))
                .with(locatedAt("job/project-name")));

        assertThat(lastCompletedBuildOf(view).url(), is("job/project-name/22/"));
    }

    /*
     * Elapsed time
     */

    @Test
    public void should_know_how_long_the_last_build_took_once_its_finished() throws Exception {
        view = a(jobView().which(new KnowsLastCompletedBuildDetails()).of(
                a(job().whereTheLast(build().finishedWith(SUCCESS).and().took(3)))));

        assertThat(lastCompletedBuildOf(view).duration(), is("3m 0s"));
    }

    /*
     * Last build, last success and last failure (ISO 8601)
     */
    @Test
    public void should_know_how_long_since_the_last_build_happened() throws Exception {
        String tenMinutesInMilliseconds = String.format("%d", 10 * 60 * 1000);

        view = a(jobView().which(new KnowsLastCompletedBuildDetails()).of(
                a(job().whereTheLast(build().startedAt("18:05:00").and().took(5))))
                .assuming(currentTime().is("18:20:00")));

        assertThat(lastCompletedBuildOf(view).timeElapsedSince(), is(tenMinutesInMilliseconds));
    }

    private KnowsLastCompletedBuildDetails.LastCompletedBuild lastCompletedBuildOf(JobView job) {
        return job.which(KnowsLastCompletedBuildDetails.class).asJson();
    }
}
