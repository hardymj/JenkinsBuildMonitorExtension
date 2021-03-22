package com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.syntacticsugar;

import com.google.common.base.Supplier;

import org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildAction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Daniel Beland
 */
public class BadgeGroovyPostbuildRecipe implements Supplier<GroovyPostbuildAction> {
    private GroovyPostbuildAction badge;

    public BadgeGroovyPostbuildRecipe() {
        badge = mock(GroovyPostbuildAction.class);
    }

    public BadgeGroovyPostbuildRecipe withText(String text) throws Exception {
        when(badge.getIconPath()).thenReturn(null);
        when(badge.getText()).thenReturn(text);
        return this;
    }

    public BadgeGroovyPostbuildRecipe withIcon(String icon, String text) throws Exception {
        when(badge.getIconPath()).thenReturn(icon);
        when(badge.getText()).thenReturn(text);
        return this;
    }

    @Override
    public GroovyPostbuildAction get() {
        return badge;
    }
}