package pages;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountPartitions {


    CREATE_ACCOUNT("Create Account"),
    SIGN_IN("Sign In");


    private String displayName;
}
