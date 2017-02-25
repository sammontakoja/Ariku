## User identification

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

#### Tests

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

## Owner management

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

#### Tests

API module
io.ariku.owner.api.OwnerServiceTest
io.ariku.owner.api.WhenClosingAttendingToCompetitionTest
io.ariku.owner.api.WhenClosingCompetitionTest
io.ariku.owner.api.WhenOpeningAttendingToCompetitionTest
io.ariku.owner.api.WhenOpeningCompetitionTest

Simple module
io.ariku.owner.simple.SimpleOwnerDatabaseTest
