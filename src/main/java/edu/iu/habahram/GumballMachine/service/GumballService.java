package edu.iu.habahram.GumballMachine.service;

import edu.iu.habahram.GumballMachine.model.GumballMachine;
import edu.iu.habahram.GumballMachine.model.GumballMachineRecord;
import edu.iu.habahram.GumballMachine.model.IGumballMachine;
import edu.iu.habahram.GumballMachine.model.TransitionResult;
import edu.iu.habahram.GumballMachine.repository.IGumballRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.List;

@Service
public class GumballService implements IGumballService{

    IGumballRepository gumballRepository;

    public GumballService(IGumballRepository gumballRepository) {
        this.gumballRepository = gumballRepository;
    }

    public TransitionResult setRecord(String id, String method) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        IGumballMachine machine = new GumballMachine(record.getId(), record.getState(), record.getCount());
        TransitionResult result;
        switch (method) {
            case "eject":
                result = machine.ejectQuarter();
                break;
            case "insert":
                result = machine.insertQuarter();
                break;
            case "crank":
                result = machine.turnCrank();
                break;
            default:
                result = null;
                break;
        }
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;
    }

    @Override
    public TransitionResult insertQuarter(String id) throws IOException {
        return setRecord(id,"insert");
    }

    @Override
    public TransitionResult ejectQuarter(String id) throws IOException {
        return setRecord(id,"eject");
    }

    @Override
    public TransitionResult turnCrank(String id) throws IOException {
        return setRecord(id,"crank");
    }

    

    @Override
    public List<GumballMachineRecord> findAll() throws IOException {
        return gumballRepository.findAll();
    }

    @Override
    public GumballMachineRecord findById(String id) throws IOException {
        return gumballRepository.findById(id);
    }

    @Override
    public String save(GumballMachineRecord gumballMachineRecord) throws IOException {
        return gumballRepository.save(gumballMachineRecord);
    }
}
