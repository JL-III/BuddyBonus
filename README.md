# BuddyBonus

Player that play together earn bonus money.

Technical requirements:
- buddy object that stores two players to be compared against
- Detect players that meet requirements
- Mechanism that runs on a minute cadence
- Mechanism that notifies players of their reward
- Mechanism that disperses reward

Requirements:
- players are not afk
- players are within 100 blocks of each other

gather the location of all players every minute  
if players are matching requirements then reward them

Player options:
- should we let players link themselves with another person or should it be automatic
- should players be rewarded for how often or the percentage of time they are near their buddy? 
this would probably require checking every second if a player is near their registered buddy.
the more i think on this the more i like the idea of a command that a player uses like /buddy <name>
where name is the person they want to buddy with.
- player who's name is selected needs to accept buddy request with /buddy accept
- we could construct the buddy object once the accept happens.
- 