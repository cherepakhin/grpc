package ru.perm.v.grpcserver.service;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.perm.v.grpcserver.helloworld.Greeting;
import ru.perm.v.grpcserver.helloworld.HelloWorldServiceGrpc.HelloWorldServiceImplBase;
import ru.perm.v.grpcserver.helloworld.Person;

@GRpcService
public class HelloWorldServiceImpl extends HelloWorldServiceImplBase {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(
          HelloWorldServiceImpl.class);

  @Override
  public void sayHello(Person request,
      StreamObserver<Greeting> responseObserver) {
    LOGGER.info("server received {}", request);

    String message = "Hello " + request.getFirstName() + " "
        + request.getLastName() + "!";
    Greeting greeting =
        Greeting.newBuilder().setMessage(message).build();
    LOGGER.info("server responded {}", greeting);

    responseObserver.onNext(greeting);
    responseObserver.onCompleted();
  }
}

