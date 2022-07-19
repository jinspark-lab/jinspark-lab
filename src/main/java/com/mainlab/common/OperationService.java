package com.mainlab.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OperationService {

    public static final int NON_TRANSACTION_ID = Integer.MAX_VALUE;
    @Autowired
    private OperationTransactionService operationTransactionService;

    public void operate(String userId, List<OperationUnit> operationUnitList) {
        Map<Integer, List<OperationUnit>> lockIdMap = operationUnitList.stream()
                .collect(Collectors.groupingBy(OperationUnit::getLockId, HashMap::new,
                        Collectors.mapping(Function.identity(), Collectors.toList())));
        try {
            for (Integer lockId : lockIdMap.keySet()) {
                List<OperationUnit> transactionList = lockIdMap.get(lockId).stream().sorted(Comparator.comparingInt(OperationUnit::getOrder)).collect(Collectors.toList());
                if (lockId == NON_TRANSACTION_ID) {
                    transactionList.forEach(transaction -> {
                        transaction.operate();
                        if (transaction.isCallbackPossible()) {
                            transaction.callback();
                        }
                    });
                } else {
                    operationTransactionService.operate(userId, transactionList);
                    transactionList.stream().filter(OperationUnit::isCallbackPossible).forEach(OperationUnit::callback);
                }
            }
        } catch (OperationNoRollbackException | OperationRollbackException ex) {
            ex.printStackTrace();
        }
    }
}
