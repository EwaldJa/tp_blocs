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

    public Bloc(String name, MetaBloc goal, World world) {
        this.blocName = name;
        this.goal = goal;
        this.world = world;
    }

    private List<MetaBloc> checkTheWorld() throws BlocNotFoundException {
        canMove = world.canMove(this);
        isPushed = inferior.isPushing();
        updateSatisfaction();
        if (canMove) { return world.getAvailableLocations(this); }
        else { return new ArrayList<>(); }
    }

    private void updateSatisfaction(){
        this.satisfaction = ((isPushed)?Constants.SCORE_FOR_BEING_PUSHED:0) + ((isPushing)?Constants.SCORE_FOR_PUSHING:0) + ((goal.equals(inferior))?Constants.SCORE_FOR_GOAL:0); }

    private void doAction() throws BlocNotFoundException, MovementUnavailableException {
        List<MetaBloc> availableLocations = checkTheWorld();
        if (satisfaction != 1.0) {
            if (!canMove) {
                isPushing = true; }
            else {
                inferior = world.moveToLocation(this, getPreferredLocation(availableLocations));
                isPushed = false;
                isPushing = false; } }
        updateSatisfaction();
    }

    private MetaBloc getPreferredLocation(List<MetaBloc> availableLocations) {
        int indexOfGoal = availableLocations.indexOf(goal);
        if (indexOfGoal != -1) {
            return availableLocations.get(indexOfGoal); }
        else {
            Random rdm = new Random();
            return availableLocations.get(rdm.nextInt(availableLocations.size())); }
    }

    @Override
    public String getBlocName() { return blocName; }

    @Override
    protected boolean isPushing() { return isPushing; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloc bloc = (Bloc) o;
        return Objects.equals(blocName, bloc.blocName);
    }

}
