package model;

public class Bloc extends MetaBloc {

    private World world;

    private String blocName;

    private MetaBloc goal;

    private MetaBloc inferior;

    @Override
    public String getBlocName() { return blocName; }
}
