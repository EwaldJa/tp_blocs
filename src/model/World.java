package model;

import exceptions.BlocNotFoundException;
import exceptions.MovementUnavailableException;

import java.util.ArrayList;
import java.util.List;

public class World {

    private int nbLocation=3, nbBlocs = 4;

    private List<List<MetaBloc>> world;

    public World(int nbLocs, int nbBlocs) {
        nbLocation = nbLocs;
        this.nbBlocs = nbBlocs;
    }

    public void initialize(Table table, List<MetaBloc> firstStack) {
        world = new ArrayList<>();
        world.add(firstStack);
        for (int i = 1; i < nbLocation; i++) {
            List<MetaBloc> loc = new ArrayList<>();
            loc.add(table);
            world.add(loc); }
    }



    public List<MetaBloc> getAvailableLocations(Bloc toCheck) {
        List<MetaBloc> availableLocations = new ArrayList<>();
        for (int i = 0; i < nbLocation - 1; i++) {
            List<MetaBloc> loc_i = world.get(i);
            MetaBloc summit = loc_i.get(loc_i.size() - 1);
            if (!summit.equals(toCheck)) {
                availableLocations.add(summit); } }
        return availableLocations;
    }

    public boolean canMove(Bloc toCheck) throws BlocNotFoundException {
        int currentLocation = getLocationOfBloc(toCheck);
        return world.get(currentLocation).indexOf(toCheck) == (world.get(currentLocation).size() - 1);
    }

    public MetaBloc moveToLocation(Bloc toMove, MetaBloc whereTo) throws MovementUnavailableException, BlocNotFoundException {
        int newLocation = getLocationOfBloc(whereTo);
        int currentLocation = getLocationOfBloc(toMove);
        List<MetaBloc> currentLoc = world.get(currentLocation);
        List<MetaBloc> newLoc = world.get(newLocation);
        if (currentLoc.indexOf(toMove) != (currentLoc.size() - 1)) { throw new MovementUnavailableException("Bloc #" + toMove.getBlocName() + " is not at the summit of the location #" + currentLocation); }
        else {
            currentLoc.remove(toMove);
            newLoc.add(toMove);
            return newLoc.get(newLoc.size() - 2); }
    }

    private int getLocationOfBloc(MetaBloc bloc) throws BlocNotFoundException {
        boolean hasToBeFirst = false;
        if (bloc instanceof Table) { hasToBeFirst = true; }
        for (int i = 0; i < nbLocation - 1; i++) {
            List<MetaBloc> loc = world.get(i);
            if (loc.indexOf(bloc) != -1) {
                if (hasToBeFirst) {
                    if (loc.indexOf(bloc) == (loc.size()-1)) {
                        return i; } }
                else {
                    return i; } } }
        throw new BlocNotFoundException("Bloc #" + bloc.getBlocName() + "does not exists in the world");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = nbBlocs; i >= 1; i--) {
            for (int j = 0; j < nbLocation - 1; j ++) {
                sb.append(blocTitle(world.get(j), i)).append(" ");
            }
            sb.append("\n"); }
        return sb.toString();
    }

    public String blocTitle(List<MetaBloc> pile, int index) {
        return ((pile.size() > index) ? ("|" + pile.get(index).getBlocName() + "|") : "| |");
    }

}
