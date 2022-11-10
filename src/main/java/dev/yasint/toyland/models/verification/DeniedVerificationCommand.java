package dev.yasint.toyland.models.verification;

import dev.yasint.toyland.models.enumerations.EVerificationStatus;

public class DeniedVerificationCommand extends VerificationCommand {

    public DeniedVerificationCommand(Verification verification) {
        super(verification);
    }

    @Override
    public boolean execute() {
        this.backupPreviousStatus();
        this.verification.setStatus(EVerificationStatus.APPROVED);
        return true;
    }

}
