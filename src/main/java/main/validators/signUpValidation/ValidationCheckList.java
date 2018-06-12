package main.validators.signUpValidation;



import main.beans.User;
import main.validators.signUpValidation.validationRules.EmailValidationRule;
import main.validators.signUpValidation.validationRules.PasswordValidationRule;
import main.validators.signUpValidation.validationRules.UsernameValidationRule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Kourlyand
 */
public class ValidationCheckList {
	public static List<RegistrationRule> validationCheck() {
		List<RegistrationRule> rules = new ArrayList<>();
		rules.add(new UsernameValidationRule());
		rules.add(new PasswordValidationRule());
		rules.add(new EmailValidationRule());
		return rules;
	}

	public static void validateClientFields(User user) throws Exception {
		for (RegistrationRule registrationRule : ValidationCheckList.validationCheck()) {
			registrationRule.validate(new RegistrationData(user));
		}
	}
}
