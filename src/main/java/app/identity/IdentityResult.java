package app.identity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IdentityResult {
    private Boolean succeeded;
    private Boolean failed;

    private List<IdentityError> identityErrors = new ArrayList<>();

    public Boolean isSucceeded() {
        return identityErrors.size() == 0;
    }


    public Boolean isFailed() {
        return identityErrors.size() > 0;
    }


    public List<IdentityError> getIdentityErrors() {
        return identityErrors;
    }

    public void setIdentityErrors(List<IdentityError> identityErrors) {
        this.identityErrors = identityErrors;
    }

    public void addIdentityError(IdentityError error){
        identityErrors.add(error);
    }
}
