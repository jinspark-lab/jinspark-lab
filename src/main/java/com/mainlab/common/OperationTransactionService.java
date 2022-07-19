package com.mainlab.common;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OperationTransactionService {

    @Transactional(isolation = Isolation.READ_COMMITTED, noRollbackFor = OperationNoRollbackException.class)
    public <T extends OperationUnit> void operate(String userId, List<T> unitList) {
        unitList.forEach(OperationUnit::operate);
    }
}
