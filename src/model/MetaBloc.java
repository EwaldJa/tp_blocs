package model;

public abstract class MetaBloc {

    public abstract String getBlocName();

    protected abstract boolean isPushing();

    @Override
    public abstract boolean equals(Object o);
}
