package hu.bme.mit.train.sensor.bootable;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.kernel.Bootable;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.sensor.TrainSensorImpl;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class TrainSensorBootable implements Bootable {

	final ActorSystem system = ActorSystem.create("Train");

	@Override
	public void startup() {
		final ActorSelection selection = system
				.actorSelection("akka.tcp://Train@127.0.0.1:2553/user/controller");
		final Future<ActorRef> resolveOne = selection.resolveOne(Duration.create(5, TimeUnit.SECONDS));
		final ActorRef actorRef;
		try {
			actorRef = Await.result(resolveOne, Duration.create(5, TimeUnit.SECONDS));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		final TrainController controller = TypedActor.get(system).typedActorOf(new TypedProps<TrainController>(TrainController.class),
				actorRef);

		final TrainSensor sensor = TypedActor.get(system)
				.typedActorOf(new TypedProps<TrainSensor>(TrainSensor.class, () -> new TrainSensorImpl(controller)), "sensor");
	}

	@Override
	public void shutdown() {
		system.terminate();
	}

}
