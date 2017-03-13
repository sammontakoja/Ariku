package io.ariku.composer;

import io.ariku.owner.NewCompetitionRequest;
import io.ariku.util.data.Competition;
import io.ariku.verification.AuthorizeRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ari Aaltonen
 */
public class AddCompetitionOwnerTest {

    // TODO Create tests

    private Composer composer;
    private Commands commands;
    private String username;

    @Before
    public void initializeValues() {
        composer = new Composer();
        username = UUID.randomUUID().toString();
        commands = new Commands(composer);
    }

    @Test
    public void plaa() {
    }

}
