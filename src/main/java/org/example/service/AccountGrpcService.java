package org.example.service;

import com.bank.management.*;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountGrpcService extends CustomerAccountsManagerGrpc.CustomerAccountsManagerImplBase {
    private static Map<Integer, Customer> cache = new HashMap<>();
    private static AtomicInteger sequence = new AtomicInteger();
    @Override
    public void createCustomerAndAccounts(AccountRequest request, StreamObserver<AccountResponse> responseObserver) {
        Customer customer = Customer.newBuilder()
                .setFirstName(request.getBody().getFirstName())
                .setLastName(request.getBody().getLastName())
                .setFullName(request.getBody().getFullName())
                .setAge(request.getBody().getAge())
                .setAge(request.getBody().getAge())
                .setAddress(request.getBody().getAddress())
                .addAllAccounts(request.getBody().getAccountsList())
                .build();
        cache.put(sequence.incrementAndGet(),customer);
        System.out.printf("CustomerId ::%d created fullName ::%s%n",sequence.get(),customer.getFullName());
        AccountResponse response =
                 AccountResponse.newBuilder()
                .setOperationStatus(true)
                .setCustomerId(sequence.get())
                .setCustomerFullName(customer.getFullName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
