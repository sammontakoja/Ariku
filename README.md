# Ariku

Ariku is an application for running shooting competitions.

## History

Finnish shooting clubs run shooting competitions with old program(s) which are full of bugs
and hard to change.

Ariku project's main idea is to create new program which really works and is simpler to use.

Ariku project started as Finnish shooting clubs wanted new competition program but
there's no reason why other than Finnish clubs or individuals cannot use this program.

Example [Olympic skeet](https://en.wikipedia.org/wiki/Olympic_skeet) is international sport
and sport's competitions are hold in over 30 countries.

## Phases

### Identification phase

##### Tests
io.ariku.console.UserVerificationTest

![](states1.png)

|Use case   | Implementation |
|---|---|
|**A1:** User navigate to sign up location | Console |
|**A2:** User fill sign up information, backend will notify if sign up was successful or failed | Console |
|**B1:** User navigate to verify sign up location | Console |
|**B2:** User fill sign up verification information, backend will notify if verification was successful or failed | Console |
|**C1:** User navigate to login location | Console |
|**C2:** User fill login information, backend's response contains security message if login was successful or failed | Console |
|**D:** User navigate to logout location and remove given security message | Console |
|**E:** Security cleaner clean all granted security messages which are older than X minutes | - |
|**F:** Authorizer ask is security message valid. Will be used when using another services | - |

## Licence

Competitions holder can use Ariku without licence for non-commercial competitions.
(By non-commercial competition we mean competition which is free to competitor.)

When Ariku is used in commercial competition then competition holder need to buy licence,
please contact sammontakoja@protonmail.com for more information.

Competitors and viewers can always use Ariku free of charge.

Ariku software licence can be found from [here](LICENSE).