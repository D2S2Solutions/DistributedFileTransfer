package com.d2s2.models;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class UnregistrationResponseModel extends AbstractRequestResponseModel {

    private int status;

    public UnregistrationResponseModel(int status) {
        this.status = status;
    }

    @Override
    public void handle() {
        System.out.println(this.status == 0 ? "Successfully unregistered" : "Error while unregistering");
    }
}
