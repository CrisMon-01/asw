package asw.grpc.hello.client.hello.grpc;

import asw.grpc.hello.client.domain.HelloServiceAdapter;
import asw.grpc.hello.proto.*;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;

@Service
public class HelloServiceAdapterGrpcImpl implements HelloServiceAdapter {

    private Logger logger = Logger.getLogger(HelloServiceAdapterGrpcImpl.class.toString());

    private ManagedChannel channel;
    private HelloServiceGrpc.HelloServiceBlockingStub blockingStub;
    private HelloServiceGrpc.HelloServiceFutureStub futureStub;

	@Value("${asw.grpc.hello.service.host}")
    private String host;
	@Value("${asw.grpc.hello.service.port}")
    private int port;

	@PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). 
				// For the example we disable TLS to avoid needing certificates.
                .usePlaintext()
                .build();
        this.blockingStub = HelloServiceGrpc.newBlockingStub(channel);
        this.futureStub = HelloServiceGrpc.newFutureStub(channel);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String sayHello(String name) {
		// return futureSayHello(name); 
		return blockingSayHello(name); 
	}

	/* Invoca sayHello usando il future stub. */ 
    public String futureSayHello(String name) {
        logger.info("sayHello(" + name + ") [FUTURE STUB]");
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
		String greeting = null; 
        try {
            ListenableFuture<HelloReply> futureReply = futureStub.sayHello(request);
			logger.info("sayHello(" + name + ") [FUTURE STUB]: qui potrei fare qualcos'altro, dopo la richiesta e prima della risposta");
            HelloReply reply = futureReply.get();
			greeting = reply.getGreeting(); 
			logger.info("sayHello(" + name + ") [FUTURE STUB]: " + greeting);
        } catch (StatusRuntimeException e) {
            logger.info("RPC failed: " + e.getStatus());
        } catch (InterruptedException e) {
            logger.info("InterruptedException: " + e.toString());
        } catch (ExecutionException e) {
            logger.info("ExecutionException: " + e.toString());
        }
        return greeting;
    }

	/* Invoca sayHello usando il blocking stub. */ 
    public String blockingSayHello(String name) {
        logger.info("sayHello(" + name + ") [BLOCKING STUB]");
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
		String greeting = null; 
        try {
            HelloReply reply = blockingStub.sayHello(request);
			greeting = reply.getGreeting(); 
			logger.info("sayHello(" + name + ") [BLOCKING STUB]: " + greeting);
        } catch (StatusRuntimeException e) {
            logger.info("RPC failed: " + e.getStatus());
        }
        return greeting;
    }

}
