package main.validators.signUpValidation.validationRules;


import main.validators.signUpValidation.RegistrationData;
import main.validators.signUpValidation.RegistrationRule;

/**
 * @author Yura Kourlyand
 */
public class UserNameValidatationRule implements RegistrationRule {
    @Override
    public void validate(RegistrationData regData) throws Exception {
        String pattern = "^(?=\\S+$)[a-zA-Z0-9]{4,12}$";

        if(!regData.getPassword().matches(pattern)){
            throw new Exception("Username must be between 4 and 12 characters contain to spaces and user only english characters and numbers");
        }
    }
}
