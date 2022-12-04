package dev.yasint.toyland.models.verification;

import dev.yasint.toyland.models.enumerations.EVerificationStatus;

/**
 * The command design pattern is used here to trigger various verification levels
 */

public abstract class VerificationCommand {

    Verification verification;
    private EVerificationStatus previousStatus;

    public VerificationCommand(Verification verification) {
        this.verification = verification;
        this.previousStatus = verification.getStatus();
    }

    public void backupPreviousStatus() {
        this.previousStatus = verification.getStatus();
    }

    public EVerificationStatus getPreviousState() {
        return this.previousStatus;
    }

    public abstract boolean execute();

}

