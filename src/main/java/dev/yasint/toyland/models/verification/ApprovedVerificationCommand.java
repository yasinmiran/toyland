package dev.yasint.toyland.models.verification;

import dev.yasint.toyland.models.enumerations.EVerificationStatus;

public class ApprovedVerificationCommand extends VerificationCommand {

    public ApprovedVerificationCommand(Verification verification) {
        super(verification);
    }

    @Override
    public boolean execute() {
        if (verification.getStatus() == EVerificationStatus.IN_PROGRESS) {
            this.backupPreviousStatus();
            this.verification.setStatus(EVerificationStatus.APPROVED);
            return true;
        }
        return false;
    }

}
