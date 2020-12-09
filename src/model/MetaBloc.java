package model;

public abstract class MetaBloc {

    public abstract String getBlocName();

    public abstract boolean isPushing();

    @Override
    public abstract boolean equals(Object o);
}
