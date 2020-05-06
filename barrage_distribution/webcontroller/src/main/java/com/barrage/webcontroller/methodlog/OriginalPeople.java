package com.barrage.webcontroller.methodlog;

import java.util.ArrayList;
import java.util.List;

@MethodLog
public class OriginalPeople {

    private List<String> sayingList;

    public OriginalPeople() {
        this.sayingList = new ArrayList<>();
    }

    public void saySomething(String saying) {
        sayingList.add(saying);
        System.out.println("saying :" + saying + " sayingList size :" + sayingList.size());
    }
}
