package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class CreateAccountPage {

    private static String firstName = "input#firstName";
    private static String lastName = "input#lastName";
    private static String password = "input#fld-p1";
    private static String confirmPassword = "input#reenterPassword";
    private static String email = "input#email";
    private static String phone = "input#phone";
    private static String recoveryPhoneCheckbox = "input#is-recovery-phone";
    private static String passwordMessage = ".c-input-error-message";
    private static String createAccountButton = "button.cia-form__controls__submit";
    private static String createAccountHeader = "//div/h1";
    private final Page page;


    public CreateAccountPage(Page page) {
        this.page = page;
    }


    public CreateAccountPage fillFirstAndLastNameFields(String firstName1, String lastName1) {
        page.locator(firstName).fill(firstName1);
        page.locator(lastName).fill(lastName1);
        return this;
    }

    public CreateAccountPage fillPasswordFields(String password1, String confirmPassword1) {
        page.locator(password).fill(password1);
        page.locator(confirmPassword).fill(confirmPassword1);
        return this;
    }

    public CreateAccountPage fillEmailField(String email1) {
        page.locator(email).fill(email1);
        return this;
    }


    public CreateAccountPage fillMobileField(String phone1) {
        page.locator(phone).fill(phone1);
        page.locator(recoveryPhoneCheckbox).check();
        return this;
    }

    public void validatePasswordMessage(String passwordMessage1) {
        PlaywrightAssertions.assertThat(page.locator(passwordMessage))
                .hasText(passwordMessage1, new LocatorAssertions.HasTextOptions().setTimeout(1000));
    }

    public void validateCreateAccountButton() {
        PlaywrightAssertions.assertThat(page.locator(createAccountButton))
                .isEnabled();
    }

    public void validateCreateAccountHeader(String createAccountHeader1) {
        PlaywrightAssertions.assertThat(page.locator(createAccountHeader))
                .hasText(createAccountHeader1);
    }
}
