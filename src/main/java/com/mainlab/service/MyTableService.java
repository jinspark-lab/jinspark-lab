package com.mainlab.service;

import com.mainlab.model.MyTable;
import com.mainlab.repository.MyTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyTableService {

    @Autowired
    private MyTableRepository myTableRepository;

    public void listMyTable() {
        List<MyTable> myTableList = myTableRepository.selectMyTableList();
        myTableList.forEach(myTable -> System.out.println(myTable.getId() + " : " + myTable.getName()));
    }

}
