package com.bnpl.aplazo.util;

import com.bnpl.aplazo.dto.client.ClientRq;

public class ValidateUtil {

    public static boolean isValidClientRq(ClientRq clientRq){

        boolean isValidName = false;
        boolean isValidDate =  false;

        if (!clientRq.getFirstName().isEmpty() && clientRq.getFirstName() != null){
            isValidName = true;
        }
        if(clientRq.getDateOfBirth() != null ){
            isValidDate = true;
        }

        return isValidDate && isValidName;

    }
}
