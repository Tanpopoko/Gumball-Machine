package edu.iu.habahram.GumballMachine.model;

public class GumballMachine implements IGumballMachine {
    final String SOLD_OUT = GumballMachineState.OUT_OF_GUMBALLS.name();
    final String NO_QUARTER = GumballMachineState.NO_QUARTER.name();
    final String HAS_QUARTER = GumballMachineState.HAS_QUARTER.name();
    final String SOLD = GumballMachineState.GUMBALL_SOLD.name();
    private String id;
    String state = SOLD_OUT;
    int count = 0;

    public GumballMachine(String id, String state, int count) {
        this.id = id;
        this.state = state;
        this.count = count;
    }

    @Override
    public TransitionResult insertQuarter() {
        boolean succeeded = false;
        String message = "";
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            message = "You can't insert another quarter";
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            state = HAS_QUARTER;
            message = "You inserted a quarter";
            succeeded = true;
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "You can't insert a quarter, the machine is sold out";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Please wait, we're already giving you a gumball";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult ejectQuarter() {
        boolean succeeded = false;
        String message = "There are no quarters to eject";
        if (count == 1) {
            count = 0;
            state = NO_QUARTER;
            succeeded = true;
            message = "Quarter Ejected";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult turnCrank() {
        boolean succeeded = false;
        String message = "Please insert a quarter first";
        if (state == HAS_QUARTER) {
            releaseBall();
            succeeded = true;
            message = "Quarter Cranked";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult refill(int gumballs) {
        count += gumballs;
        boolean succeeded = true;
        String message = gumballs + " gumballs Added";
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public void changeTheStateTo(GumballMachineState name) {

    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public String getTheStateName() {
        return null;
    }

    @Override
    public void releaseBall() {
        if (count > 0) {
            count--;
        }
    }


}
