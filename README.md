# Ariku ![Build status](https://travis-ci.org/sammontakoja/Ariku.svg?branch=master)

Ariku is platform for creating different kind of competitions.

## History

Finnish shooting clubs run shooting competitions with old program(s) which are full of bugs
and hard to change.

Ariku project's original idea was to create new program for them which works and is simpler to use.

Original plan grew little larger and now Ariku project's focus is on providing services to any kind of competition.

### User identification

|Use case   | Implemented in modules |
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

##### Tests

Unit module:
io.ariku.verification.api.UserVerificationServiceTest
io.ariku.verification.simple.SimpleUserVerificationDatabaseTest

Simple module:
io.ariku.verification.simple.SimpleUserVerificationDatabaseTest

Console module:
io.ariku.console.verification.SignUpIT
io.ariku.console.verification.VerifySignUpIT
io.ariku.console.verification.LogoutIT
io.ariku.console.verification.SignUpIT

### Owner management

|Use case   | Implemented in modules|
|---|---|
|User can create new competition and became competition owner | API, Simple |
|Owner can list all of his/her competitions | API, Simple |
|Owner can add another user to be competition's owner as well using userid | API, Simple |
|Owner can remove another competition's owner using userid | API, Simple |
|Owner navigate to modify competition state location | - |
|Owner can open attending to competition | API, Simple |
|Owner can close attending to competition | API, Simple |
|Owner can start competition | API, Simple|
|Owner can close competition | API, Simple|

##### Tests

API module
io.ariku.owner.api.OwnerServiceTest
io.ariku.owner.api.WhenClosingAttendingToCompetitionTest
io.ariku.owner.api.WhenClosingCompetitionTest
io.ariku.owner.api.WhenOpeningAttendingToCompetitionTest
io.ariku.owner.api.WhenOpeningCompetitionTest

Simple module
io.ariku.owner.simple.SimpleOwnerDatabaseTest

## Licence

Competitions holder can use Ariku without licence for non-commercial competitions.
(By non-commercial competition we mean competition which are free to all competitors.)

When Ariku is used in commercial competition then competition holder need to buy licence,
please contact sammontakoja@protonmail.com for more information.

Competitors and viewers can always use Ariku free of charge.

Ariku software licence can be found from [here](LICENSE).