package ru.ulmc.communityFeedback.conf.reference;

/**
 * User: Kolmogorov Alex
 * Date: 07.12.2015
 */
public abstract class AbstractConfParam<T> {
    protected String name;
    protected T defaultValue;

    public AbstractConfParam(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }
}
