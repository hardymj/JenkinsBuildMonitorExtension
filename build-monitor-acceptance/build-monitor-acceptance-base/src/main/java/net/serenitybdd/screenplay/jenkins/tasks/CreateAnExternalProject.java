package net.serenitybdd.screenplay.jenkins.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.jenkins.user_interface.NewJobPage;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateAnExternalProject implements Task {
    public static CreateAnExternalProject called(String name) {
        return instrumented(CreateAnExternalProject.class, name);
    }

    @Override
    @Step("{0} creates a 'Freestyle Project' called '#name'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                CreateAProject.called(name)
                        .ofType(NewJobPage.External_Project)
        );
    }

    public CreateAnExternalProject(String jobName) {
        this.name = jobName;
    }

    private final String   name;
}
