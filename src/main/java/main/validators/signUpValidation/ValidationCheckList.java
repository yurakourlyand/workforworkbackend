package main.validators.signUpValidation;



import main.beans.User;
import main.validators.signUpValidation.validationRules.EmailValidatationRule;
import main.validators.signUpValidation.validationRules.PasswordValidatationRule;
import main.validators.signUpValidation.validationRules.UserNameValidatationRule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Kourlyand
 */
public class ValidationCheckList {
	public static List<RegistrationRule> validationCheck() {
		List<RegistrationRule> rules = new ArrayList<>();
		rules.add(new UserNameValidatationRule());
		rules.add(new PasswordValidatationRule());
		rules.add(new EmailValidatationRule());
		return rules;
	}

	public static void validateClientFields(User user) throws Exception {
		for (RegistrationRule registrationRule : ValidationCheckList.validationCheck()) {
			registrationRule.validate(new RegistrationData(user));
		}
	}
}
