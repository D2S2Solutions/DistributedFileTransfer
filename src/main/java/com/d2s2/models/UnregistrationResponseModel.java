package com.d2s2.models;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class UnregistrationResponseModel extends AbstractRequestResponseModel {

    int status;

    public UnregistrationResponseModel(int status) {
        this.status = status;
    }

    @Override
    public void handle() {

        if (this.status == 0) {
            System.out.println("Successfully unregistered");
        } else {
            System.out.println("Error while unregistering");
        }

    }
}
