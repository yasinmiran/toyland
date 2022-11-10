package dev.yasint.toyland.models.verification;

import dev.yasint.toyland.models.enumerations.EVerificationStatus;

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

