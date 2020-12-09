package model;

import exceptions.BlocNotFoundException;
import exceptions.MovementUnavailableException;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Bloc extends MetaBloc {

    private World world;

    private double satisfaction;

    private boolean canMove, isPushed, isPushing;

    private String blocName;

    private MetaBloc goal;
    private MetaBloc inferior;

    private List<MetaBloc> availableLocations;

    public Bloc(String name, MetaBloc goal, World world) {
        this.blocName = name;
        this.goal = goal;
        this.world = world;
    }

    public void percept() throws BlocNotFoundException {
        canMove = world.canMove(this);
        isPushed = inferior.isPushing();
        if (canMove) { availableLocations = world.getAvailableLocations(this); }
        else { availableLocations = new ArrayList<>(); }
    }

    private void updateSatisfaction(){
        this.satisfaction = ((isPushed)?Constants.SCORE_FOR_BEING_PUSHED:0) + ((isPushing)?Constants.SCORE_FOR_PUSHING:0) + ((goal.equals(inferior))?Constants.SCORE_FOR_GOAL:0); }

    public void action() throws BlocNotFoundException, MovementUnavailableException {
        updateSatisfaction();
        if (satisfaction != 1.0) {
            if (!canMove) {
                isPushing = true; }
            else {
                if (availableLocations.size() > 0) {
                    inferior = world.moveToLocation(this, getPreferredLocation(availableLocations));
                    isPushed = false;
                    isPushing = false; } } }
        updateSatisfaction();
    }

    private MetaBloc getPreferredLocation(List<MetaBloc> availableLocations) {
        Random rdm = new Random();
        int indexOfGoal = availableLocations.indexOf(goal);
        if ((indexOfGoal != -1) && (rdm.nextInt(10) > 3)) {
            return availableLocations.get(indexOfGoal); }
        else {
            return availableLocations.get(rdm.nextInt(availableLocations.size())); }
    }

    @Override
    public String getBlocName() { return blocName; }

    @Override
    public boolean isPushing() { return isPushing; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloc bloc = (Bloc) o;
        return Objects.equals(blocName, bloc.blocName);
    }

    public void setInferior(MetaBloc inferior) { this.inferior = inferior; }

    @Override
    public String toString() {
        return String.format("Bloc %s [isPushing %b, canMove %b, isPushed %b, satisfaction %f, goal %s, inferior %s, availablelocations %s]", blocName, isPushing, canMove, isPushed, satisfaction, goal.getBlocName(), inferior.getBlocName(), getAvailableLocationsNames());
    }

    public double getSatisfaction() {
        return satisfaction;
    }

    private String getAvailableLocationsNames() {
        StringBuilder sb = new StringBuilder();
        for (MetaBloc mb:availableLocations) {
            sb.append(mb.getBlocName()).append(","); }
        return sb.toString();
    }
}
