package com.rail.card.ticket.support;

import java.util.function.Function;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptWrapper {
    private final int logRounds;

    public BcryptWrapper(int logRounds) {
        this.logRounds = logRounds;
    }

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(this.logRounds));
    }

    public boolean verifyHash(String password, String hash, Function<String, Boolean> update) {
        return BCrypt.checkpw(password, hash);
    }
}
