package dev.yasint.toyland.models.verification;

import dev.yasint.toyland.models.enumerations.EVerificationStatus;

public class InProgressVerificationCommand extends VerificationCommand {

    public InProgressVerificationCommand(Verification verification) {
        super(verification);
    }

    @Override
    public boolean execute() {
        if (verification.getStatus() == EVerificationStatus.CREATED) {
            this.backupPreviousStatus();
            this.verification.setStatus(EVerificationStatus.IN_PROGRESS);
            return true;
        }
        return false;
    }

}
