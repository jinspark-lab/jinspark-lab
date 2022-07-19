package com.mainlab.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class OperationCallback implements OperationUnit, Serializable {
    private static final long serialVersionUID = -7780255286328343701L;
    private static final OperationCallback EMPTY = new OperationCallback(() -> {
    });
    private final int lockId;
    private final int order;
    private final OperationUnit operationUnit;

    public OperationCallback(OperationUnit operationUnit) {
        this.lockId = 0;
        this.order = 0;
        this.operationUnit = operationUnit;
    }

    public OperationCallback(int order, OperationUnit operationUnit) {
        this.lockId = 0;
        this.order = order;
        this.operationUnit = operationUnit;
    }

    @Override
    public void operate() {
        getOperationUnit().operate();
    }

    public OperationUnit getOperationUnit() {
        return Optional.ofNullable(operationUnit).orElse(EMPTY);
    }
}
