package model;

import utils.Constants;

import java.util.Objects;

public class Table extends MetaBloc {

    private String blocName = Constants.TABLE_NAME;

    public Table(){}

    @Override
    public String getBlocName() { return blocName;}

    @Override
    public boolean isPushing() { return false; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table bloc = (Table) o;
        return Objects.equals(blocName, bloc.blocName);
    }
}
