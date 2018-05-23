package org.rallyplugins.commons.deliverable;

import org.rallyplugins.commons.utils.ResourceUtils;

import java.io.IOException;

public abstract class AbstractRowBuilder {

    protected boolean multPresenter;

    protected AbstractRowBuilder(boolean multPresenter) {
        this.multPresenter = multPresenter;
    }

    protected String formatTemplate(Object... args) throws IOException {
        String tpl = getTplFromFile(getTplPath());
        return String.format(tpl, args);
    }

    public String getTplFromFile(String tplPath) throws IOException {
        return ResourceUtils.getTplFromFile(tplPath);
    }

    public abstract String buildRow() throws IOException;

    public abstract String getTplPath();



}
