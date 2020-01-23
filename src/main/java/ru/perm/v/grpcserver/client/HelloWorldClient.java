package ru.perm.v.grpcserver.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.perm.v.grpcserver.helloworld.Greeting;
import ru.perm.v.grpcserver.helloworld.HelloWorldServiceGrpc;
import ru.perm.v.grpcserver.helloworld.Person;

@Component
public class HelloWorldClient {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(HelloWorldClient.class);

  private HelloWorldServiceGrpc.HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;

  @PostConstruct
  private void init() {
    ManagedChannel managedChannel = ManagedChannelBuilder
        .forAddress("localhost", 6565).usePlaintext().build();

    helloWorldServiceBlockingStub =
        HelloWorldServiceGrpc.newBlockingStub(managedChannel);
  }

  public String sayHello(String firstName, String lastName) {
    Person person = Person.newBuilder().setFirstName(firstName)
        .setLastName(lastName).build();
    LOGGER.info("client sending {}", person);

    Greeting greeting =
        helloWorldServiceBlockingStub.sayHello(person);
    LOGGER.info("client received {}", greeting);

    return greeting.getMessage();
  }
}
