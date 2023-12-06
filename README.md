# BuddyBonus

Players that play together earn bonus money together.

Todo:
- disperse reward
- add player options for notification
- add bonus modifiers over time
- add admin command to disband buddies
- add admin command to reload plugin
- stylize messages to players
- add messages for when players don't meet command requirements
- consider dc buddy removals, could just let the buddy object stay until someone uses a `/buddy remove` command.

How to use:
- `/buddy <name>` - send a buddy request to someone
- `/buddy accept` - accepts incoming request
- `/buddy remove` - remove your buddy
- `/buddy` - check if you have a buddy and if so, who it is.

admin:
- `/abuddy list` - admin command to see all buddies and requests


Requirements:
- players must send requests every time they log in or out (could get annoying for people who dc)
- players are not afk
- players are within 100 blocks of each other