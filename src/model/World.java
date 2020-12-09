package model;

import exceptions.BlocNotFoundException;
import exceptions.MovementUnavailableException;

import java.util.ArrayList;
import java.util.List;

public class World {

    private int nbLocation=3;

    private List<List<MetaBloc>> world;

    public World(int nbLocs, Table table) {
        nbLocation = nbLocs;
        world = new ArrayList<>();
        for (int i = 0; i < nbLocs; i++) {
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
        return world.get(currentLocation).indexOf(toCheck) == world.get(currentLocation).size();
    }

    public MetaBloc moveToLocation(Bloc toMove, MetaBloc whereTo) throws MovementUnavailableException, BlocNotFoundException {
        int newLocation = getLocationOfBloc(whereTo);
        int currentLocation = getLocationOfBloc(toMove);
        List<MetaBloc> currentLoc = world.get(currentLocation);
        List<MetaBloc> newLoc = world.get(newLocation);
        if (currentLoc.indexOf(toMove) != currentLoc.size()) { throw new MovementUnavailableException("Bloc #" + toMove.getBlocName() + " is not at the summit of the location #" + currentLocation); }
        else {
            currentLoc.remove(toMove);
            newLoc.add(toMove);
            return newLoc.get(newLoc.size() - 2); }
    }

    private int getLocationOfBloc(MetaBloc bloc) throws BlocNotFoundException {
        for (int i = 0; i < nbLocation - 1; i++) {
            List<MetaBloc> loc = world.get(i);
            if (loc.indexOf(bloc) != -1) { return i; } }
        throw new BlocNotFoundException("Bloc #" + bloc.getBlocName() + "does not exists in the world");
    }

    public String toString() {
        //TODO: toString
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
