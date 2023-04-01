package hu.bme.mit.train.sensor;

import com.google.common.collect.Table;
import hu.bme.mit.train.controller.TrainControllerImpl;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.user.TrainUserImpl;
import org.junit.Before;
import org.junit.Test;

public class TrainSensorTest {

    TrainSensor sensor;
    TrainController controller;
    TrainUser user;
    @Before
    public void before() {
        controller = new TrainControllerImpl();
        user = new TrainUserImpl(controller);
        sensor = new TrainSensorImpl(
                controller,
                user
        );
    }

    @Test
    public void ThisIsAnExampleTestStub() {

        sensor.saveData();

        Table<Long, Integer, Integer> data = sensor.getData();
        assert(data.size() == 1);

        sensor.overrideSpeedLimit(5);
        user.overrideJoystickPosition(5);
        controller.followSpeed();
        controller.followSpeed();
        sensor.saveData();

        boolean first = true;
        for (Table.Cell<Long, Integer, Integer> cell: sensor.getData().cellSet()){
            var time = cell.getRowKey();
            var joystickPosition = cell.getColumnKey();
            var referenceSpeed = cell.getValue();

            if(first){
                assert(joystickPosition == 0);
                assert(referenceSpeed == 0);
                first = false;
            }
            else{
                assert(joystickPosition == 5);
                assert(referenceSpeed == 5);
            }
        }
        //assert(joystickPosition);
    }
}
