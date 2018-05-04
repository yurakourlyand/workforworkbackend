package main.validators.signUpValidation.validationRules;


import main.validators.signUpValidation.RegistrationData;
import main.validators.signUpValidation.RegistrationRule;
import org.apache.commons.validator.routines.EmailValidator;



/**
 * @author Yura Kourlyand
 */
public class EmailValidatationRule implements RegistrationRule {
    @Override
    public void validate(RegistrationData regData) throws Exception {
       boolean validMail = EmailValidator.getInstance().isValid(regData.getEmail());
        if(!validMail){
            throw new Exception("Invalid email");//todo this password
        }
    }
}
