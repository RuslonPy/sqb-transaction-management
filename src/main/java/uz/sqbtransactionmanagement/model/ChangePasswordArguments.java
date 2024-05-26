package uz.sqbtransactionmanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordArguments extends GenericArguments {
    //I could handle it if there were more operations that I could have. It can be user fields.
    //For more info, we have checked user's attempts, counted if he was wrong, if successfully
    //he tried, message about success otherwise try again msg. After 5 or any other wrong attempts
    //he has to be blocked for kinda time, in my case, 5 min.
    protected String newPassword;
    protected String oldPassword;
}
