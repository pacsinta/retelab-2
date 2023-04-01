package hu.bme.mit.train.interfaces;

import com.google.common.collect.Table;

public interface TrainSensor {

	int getSpeedLimit();

	void overrideSpeedLimit(int speedLimit);

	void saveData();

	Table<Long, Integer, Integer> getData();
}
