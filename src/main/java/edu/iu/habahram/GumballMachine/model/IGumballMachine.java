package edu.iu.habahram.GumballMachine.model;

public interface IGumballMachine {
    TransitionResult insertQuarter();
    TransitionResult ejectQuarter();
    TransitionResult turnCrank();
    TransitionResult refill(int gumballs);
    void changeTheStateTo(GumballMachineState name);
    Integer getCount();
    String getTheStateName();

    void releaseBall();
}
