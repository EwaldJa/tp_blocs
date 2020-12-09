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

    public MetaBloc[] getAvailableLocations(Bloc toCheck) throws BlocNotFoundException {
        int currentLocation = getLocationOfBloc(toCheck);
        MetaBloc[] availableLocations = new MetaBloc[nbLocation - 1];
        for (int i = 0; i < nbLocation - 1; i++) {
            if (i != currentLocation) {
                List<MetaBloc> loc_i = world.get(i);
                availableLocations[i] = loc_i.get(loc_i.size() - 1);
            }
        }
        return availableLocations;
    }

    public boolean canMove(Bloc toCheck) throws BlocNotFoundException {
        int currentLocation = getLocationOfBloc(toCheck);
        return world.get(currentLocation).indexOf(toCheck) == world.get(currentLocation).size();
    }

    public MetaBloc moveToLocation(Bloc toMove, Bloc whereTo) throws MovementUnavailableException, BlocNotFoundException {
        int newLocation = getLocationOfBloc(whereTo);
        int currentLocation = getLocationOfBloc(toMove);
        List<MetaBloc> currentLoc = world.get(currentLocation);
        List<MetaBloc> newLoc = world.get(newLocation);
        if (currentLoc.indexOf(toMove) != currentLoc.size()) { throw new MovementUnavailableException("Bloc #" + toMove.getBlocName() + " is not at the summit of the location #" + currentLocation); }
        else {
            currentLoc.remove(toMove);
            newLoc.add(toMove);
            return newLoc.get(newLoc.size() - 2);
        }
    }

    private int getLocationOfBloc(MetaBloc bloc) throws BlocNotFoundException {
        for (int i = 0; i < nbLocation - 1; i++) {
            List<MetaBloc> loc = world.get(i);
            if (loc.indexOf(bloc) != -1) { return i; }
        }
        throw new BlocNotFoundException("Bloc #" + bloc.getBlocName() + "does not exists in the world");
    }

    public String toString() {
        //TODO: toString
    }
}
