# Ariku ![Build status](https://travis-ci.org/sammontakoja/Ariku.svg?branch=master)

Ariku is platform for creating different kind of competitions.

## History

Finnish shooting clubs run shooting competitions with old program(s) which are full of bugs
and hard to change.

Ariku project's original idea was to create new program for them which works and is simpler to use.

Original plan grew little larger and now Ariku project's focus is on providing services to any kind of competition.

## Architecture

### User identification

|Use case   | Implemented with|
|---|---|
|User navigate to sign up location | API, Console |
|User fill sign up information, backend will notify if sign up was successful or failed | API, Console |
|User navigate to verify sign up location | API, Console |
|User fill sign up verification information, backend will notify if verification was successful or failed | API, Console |
|User navigate to login location | API, Console |
|User fill login information, backend's response contains security message if login was successful or failed | API, Console |
|User navigate to logout location and remove given security message | API, Console |
|Security cleaner clean all granted security messages which are older than X minutes | API, Console |
|System ask is security message valid. Will be used when using another services | API, Console |

##### Unit tests
io.ariku.verification.api.UserVerificationServiceTest
io.ariku.verification.simple.SimpleUserVerificationDatabaseTest

##### Integration tests
io.ariku.console.verification.SignUpIT
io.ariku.console.verification.VerifySignUpIT
io.ariku.console.verification.LogoutIT
io.ariku.console.verification.SignUpIT

### Owner management

|Use case   | Implemented with |
|---|---|
|User can create new competition and became competition owner | API |
|Owner can list all of his/her competitions | API |
|Owner can add another user to be competition's owner as well using userid | API |
|Owner can remove another competition's owner using userid | API |
|Owner navigate to modify competition state location | - |
|Owner can open attending to competition | API |
|Owner can close attending to competition | API |
|Owner can start competition | - |
|Owner can close competition | - |

##### Unit tests
-

##### Integration tests
-

## Licence

Competitions holder can use Ariku without licence for non-commercial competitions.
(By non-commercial competition we mean competition which is free to competitor.)

When Ariku is used in commercial competition then competition holder need to buy licence,
please contact sammontakoja@protonmail.com for more information.

Competitors and viewers can always use Ariku free of charge.

Ariku software licence can be found from [here](LICENSE).