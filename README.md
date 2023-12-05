# BuddyBonus

Players that play together earn bonus money together.

Todo:
- disperse reward
~~- add config for timing and reward amounts~~
- add player options for notification
- add bonus modifiers over time
- add `/buddy` command so players can see who their buddy is
- stylize messages to players
- add debug option to config for console messages
- consider dc buddy removals, could just let the buddy object stay until someone uses a `/buddy remove` command.

How to use:
- `/buddy <name>` - send a buddy request to someone
- `/buddy accept` - accepts incoming request
- `/buddy remove` - remove your buddy

Requirements:
- players must send requests every time they log in or out (could get annoying for people who dc)
- players are not afk
- players are within 100 blocks of each other