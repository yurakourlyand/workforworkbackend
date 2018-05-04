package main.validators.signUpValidation;

/**
 * @author Yura Kourlyand
 */
public interface RegistrationRule {
    void validate(RegistrationData regData) throws Exception;
}
