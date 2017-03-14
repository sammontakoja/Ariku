package io.ariku.composer;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Ari Aaltonen
 */
public class AddCompetitionOwnerRecordTest {

    private Composer composer;
    private Commands commands;
    private String usernameOfUserA;
    private String usernameOfUserB;

    @Before
    public void initializeValues() {
        composer = new Composer();
        usernameOfUserA = UUID.randomUUID().toString();
        usernameOfUserB = UUID.randomUUID().toString();
        commands = new Commands(composer);
    }

    @Test
    public void userA_add_userB_as_competition_owner_ok() {
        String securityTokenOfUserA = commands.loginWithUsername(usernameOfUserA);

    }

}
