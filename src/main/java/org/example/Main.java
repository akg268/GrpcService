package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.example.service.AccountGrpcService;
import io.grpc.protobuf.services.HealthStatusManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Starting the server!");
        HealthStatusManager health = new HealthStatusManager();
        Server server = ServerBuilder.forPort(8080)
                .addService(ServerInterceptors.intercept(
                        new AccountGrpcService()))
                .addService(health.getHealthService())
                .addService(ProtoReflectionService.newInstance())
                .build();
        server.start();
        health.setStatus("", HealthCheckResponse.ServingStatus.SERVING);
        server.awaitTermination();
    }
}