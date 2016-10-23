## External architecture

### User stories

At the start of project different sport rules like Skeet and Trap are ignored
and concentration is focused on creating common modules which must be created first.

Ariku must provide services to three different user groups:
1. competition holders
1. competitors
1. viewers.

Competition holder and competitor must be **identified** users, otherwise anyone can change
any competition data.

#### When user is identified by Ariku

When user want to either participate or start new competition he/she must be logged in.
User can login only when user has signed up.

1. Sign up. When signing up user must have a name.
1. Verify sign up.
1. Login
1. Logout

#### When user create competition and become competition holder
 
1. Logged in user create new competition. Competition must have name.
1. Logged in user can delete own competition.

#### When user participate to competition and become competitor

1. Logged in user participate to competition.
1. Logged in user cancel participating to competition.

#### When anyone who want to see created competition as viewer

1. Anyone can see created competitions.
  
## Internal architecture

Main module structure:

1. verification
1. competition holder
1. participant
1. viewer

Codebase is written with Java 8.
 
Modules are built with maven.