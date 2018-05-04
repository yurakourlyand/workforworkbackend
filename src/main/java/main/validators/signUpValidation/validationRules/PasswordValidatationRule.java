package main.validators.signUpValidation.validationRules;



import main.validators.signUpValidation.RegistrationData;
import main.validators.signUpValidation.RegistrationRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yura Kourlyand
 */
public class PasswordValidatationRule implements RegistrationRule {
    @Override
    public void validate(RegistrationData regData) throws Exception {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,16}$";
        Matcher m = Pattern.compile(pattern).matcher(regData.getPassword());
        if(!m.matches()){
            throw new Exception("Password must be between 8 and 16 characters and contain at least one latter and at least one number");
        }
    }
}
