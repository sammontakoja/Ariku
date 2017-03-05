## User identification (verification-module)

|Use case | verified to work with modules |
|---|---|
|User navigate to sign up location | database:simple, gui:console
|User fill sign up information, backend will notify if sign up was successful or failed | database:simple, gui:console
|User navigate to verify sign up location | database:simple, gui:console
|User fill sign up verification information, backend will notify if verification was successful or failed | database:simple, gui:console
|User navigate to login location | Console | Simple
|User fill login information, backend's response contains security message if login was successful or failed | database:simple, gui:console
|User navigate to logout location and remove given security message | database:simple, gui:console
|Security cleaner clean all granted security messages which are older than X minutes | database:simple, gui:console
|System ask is security message valid. Will be used when using another services | database:simple, gui:console

## Owner management (owner-module)

|Use case | verified to work with modules |
|---|---|
|User can create new competition and became competition owner | database:simple, gui:console|
|Owner can list all of his/her competitions | database:simple, gui:console |
|Owner can add another user to be competition's owner as well using userid | database:simple, TODO gui:console |
|Owner can remove another competition's owner using userid | database:simple |
|Owner navigate to modify competition state location | - |
|Owner can open attending to competition | database:simple |
|Owner can close attending to competition | database:simple |
|Owner can start competition | database:simple |
|Owner can close competition | database:simple |

## User management (user-module)

|Use case | verified to work with modules |
|---|---|
|User can participate to competition when competition is open to participating | database:simple |
|User can cancel participating when competition is open to participating | database:simple |
|User can list competition(s) which he/she is participating| database:simple |