package dev.yasint.toyland.models.verification;

import dev.yasint.toyland.models.enumerations.EVerificationStatus;

/**
 * Triggered when a verification request is initiated
 */

public class CreatedVerificationCommand extends VerificationCommand {

    public CreatedVerificationCommand(Verification verification) {
        super(verification);
    }

    @Override
    public boolean execute() {
        this.backupPreviousStatus();
        this.verification.setStatus(EVerificationStatus.CREATED);
        return true;
    }

}
