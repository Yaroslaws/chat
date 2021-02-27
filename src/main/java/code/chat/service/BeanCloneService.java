package code.chat.service;

import code.chat.Repo.MyServiceBean;

public class BeanCloneService implements MyServiceBean {

    @Override
    public void startEngine() {
        System.out.println("startEngine");
    }

    @Override
    public void stopEngine() {
        System.out.println("stopEngine");
    }
}
