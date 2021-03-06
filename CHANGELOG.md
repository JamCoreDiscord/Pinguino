# Pinguino Changelog

## Version 0.9.1

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/comp/v0.9.0...v0.9.1)**

### Fixes

- A NPE is no longer thrown when using `/thread archive` in some situations.

### Features

- Change main package from `io.github.jamalam` to `io.github.jamalam.pinguino`.
- Include time of delivery when setting a reminder.
- Notify the user if a reminder was delivered late.

## Version 0.9.0

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/comp/v0.8.2...v0.9.0)**

### Fixes

- Yet more database timeouts have been fixed - this particular one was effecting `/thread save`.
- Throw on invalid `LOG_LEVEL` environment variable.
- Create the `./logs` directory if it is not present.
- Nickname updated logs now ues the nickname rather than the username.
- Remove duplicate `Channel Locked` message.
- `Thread Saved` logs are no longer sent twice on thread creation.

### Features

- Dynamically fetch Pinguino's profile picture so that it's always up-to-date when used in responses.
- Use `task` for development.
- Proper scheduling, finally! Mutes, locks and scheduled messages function correctly and will persist between restarts. 
- Add a `/remind` command

## Version 0.8.2

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.8.1...v0.8.2)**

### Fixes

- Fixed an issue where `/fun age` failed if you used a user mention.
- Removed trailing comma from user info commands.
- Fixed issue where phishing features wouldn't register.
- Attempt fix for `MongoSocketRead` and `MongoSocketWrite` exceptions.
- Detekt bug fixed - Detekt now works again!
- Following on from that...fix _all_ Detekt issues that built up while it wasn't running.
- Issue when no public mod log channel set and using ban commands.

### Features

- Add response to `Announce` message command.
- Append `latest-` before latest log name.
- Add `/admin log *` commands to upload logs to Discord.
- Add more control over bot statuses using admin commands.
- Improve message deleted logs.

### Notes

Here's some statistics about the code base as of V0.8.2, because why not:

Kotlin Files: 47 Blank Kotlin Lines: 929 Comment Kotlin Lines: 873 Code Kotlin Lines: 5416

That's more code than I thought!

## Version 0.8.1

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.8.0...v0.8.1)**

### Fixes

- It looks like the `Announce` message command was lost along the git history at some point? It's now been added back.
- Make log file name compatible with Linux.

## Version 0.8.0

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.8...v0.8.0)**

### Fixes

- Remove `database.backup()` in favour of searching for a solution disconnected from the bot.

### Features

- Improve database caching.
- Add a bot announcement system.
- Add the role module, which provides 'reaction role'-like functionality using slash commands rather than reactions.

## Version 0.7.8

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.7...v0.7.8)**

### Fixes

- Use kord `1.5.2-RC1` rather than `SNAPSHOT` for more stability.

### Features

- **Database backups**.
- Split roles updated logs into added/removed for easier reading.
- Add more supported file extensions to the File Paste Module.

## Version 0.7.7

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.6...v0.7.7)**

### Fixes

- Removed publishing logging as it was acting up (the event Discord was emitting was strange).
- Add code tags to all IDs in logging.
- Remove `.lowercase()` from tag calls.
- Logging for `/delete-config` and `/leave` now works.
- Fix commands that take optional channel arguments error-ing out.
- Spell check the documentation.
- Fixed an issue with `/thread save`.

### Features

- Limit tags to 2000 characters in size.
- Improve formatting for `/tag use`.
- Add `/tag edit` command.
- Add tag content to tag deleted logs.
- Check some commands are not executed in DMs.
- Add `/module tags enable` and `/module tags disable`.

## Version 0.7.6

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.5...v0.7.6)**

### Fixes

- Remove `/shorten-link` as it used a broken API.
- Fix issue link in error response (broken hyperlink).
- Fixed error with admin commands when running in dev.
- Fixed an issue when specifying a duration longer than 30 days in mute commands.
- Explicitly depend on KordEx `extra-common`, rather than relying on transient dependencies.
- Clean up Gitignore.
- Increase DBL posting delay (to stop rate limit).
- Fix typos in some presence statuses.
- Fix incorrect log title when creating tag (`deleted` instead of `created`).
- Fix incorrect `/user-info` response.

### Features

- Add message content and link to reaction removed logging.
- Add an `/admin server count` admin command.
- Log files are now saved in `/logs/` and have a date/time based name.
- Config files no longer use `.env`, the bot is configured using a `config.yml`
  file for better UX.
- Add autocomplete to `/tag use` and `/tag delete`.
- Add tag requester username to `/tag use` response.
- Add `/fun color`.

## Version 0.7.5

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.4...v0.7.5)**

### Fixes

- Fix building on GitHub Actions by adding Kord snapshot repository.
- Fix `Playing TV` status to be `Watching TV`.
- Move `/uptime` to `UserUtilityExtension`.
- Fix `/module phishing set-discipline-level` not appearing.

### Features

- Only append to log file in production.
- Add GitHub issue link to error response.
- Add admin commands controlled by the `ADMIN_SERVER_ID` and `ADMIN_ID` environment variables.

## Version 0.7.4

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.3...v0.7.4)**

### Fixes

- Hastebin upload logs now use the correct colour.
- Increase presence-setting delay on initial startup to reduce errors.
- Only the author of a message can upload its contents to Hastebin.
- Improve appearance of error messages.
- Improved the formatting of dates/times in responses.
- Fixed an error with member update logging and message update logging.

### Features

- Log levels can now be specified through the `LOG_LEVEL` environment variable (default `INFO`).
- Logs are now sent to a file (`pinguino.log`), which can be configured using the `LOG_FILE`
  environment variable.
- Uses of the `/embed` command are now logged.
- Add a `/uptime` command
- Add new status messages:
    - `Listening to 103.5, DawnFM`
    - `Listening to Two Door Cinema Club`
    - `Listening for {uptime}`
    - `Watching the world burn`
    - `Watching over your server`
    - `Wathcing for scammers`
    - `Watching TV`
    - `Playing the piano`
    - `Playing for {uptime}`
- Added extra information to some logs where necessary.
- Add `/module phishing set-moderators-exempt` to set whether phishing checks should be applied to moderators.

## Version 0.7.3

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.2...v0.7.3)**

### Fixes

- Urgent: database calls now work.

## Version 0.7.2

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.1...v0.7.2)**

### Fixes

- Commas now come before newlines in logs.
- Discriminators in user-info commands are now prefixed with `#`.
- Attempted to make the database connection more stable.

### Features

- User info command's `Created At` field now show relative time as well.
- You can now specify a moderation level for posting a phishing domain (delete, kick, ban).

## Version 0.7.1

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.7.0...v0.7.1)**

### Fixes

- Bots being unmuted will now be logged.
- Message edited, quote sent and `/ask` used logs now include the relevant channel.

### Features

- You can no longer quote yourself/your own messages.
- New logging features:
    - Reaction removed.
    - Message scheduled.
    - Member nickname updated.
    - Member avatar updated.
    - Member roles added/removed.
    - Guild role added/updated/removed.
    - Message pinned/unpinned.
    - Message published.
- Durations in logs are now more readable.
- KordEx built-in error responses now use embeds.
- A new phishing-detection extension.
- Add a ping command
- Add a user-information command (context and slash).

## Version 0.7.0

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.6.1...v0.7.0)**

### Fixes

- When channels are locked, the message sent to the channel is now an embed, to be in line with the rest of the bot.
- The code package is now `io.github.jamalam`, changed from `io.github.jamalam360`, to reflect my username transitioning
  I have been attempting to do.
- Sending `@Pinguino` to get the help message now works with trailing whitespace.
- Mod-log unmute logging now uses the correct embed colour.
- Logging events now respect whether the logging module is enabled or disabled.
- Trying to quote when the quotes channel has not been configured now responds accordingly.

### Features

- Mute commands now use native discord timeouts, rather than a custom system. This means that the duration argument for
  mute commands is no longer optional.
- Added logging for the modification of guild emojis.
- Update to latest KordEx snapshots.

## Version 0.6.1

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.6.0...v0.6.1)**

Note: for this release, I have updated the structure of the changelog, as an ongoing change.

### Fixes

- Fix an error caused by an unsafe cast.
- Quotes created by reacting with `:star:` no longer quote multiple times for each reaction.
- Fixed the formatting of `/fun coin` response.
- `/fun` commands now mention users correctly.
- `/module [enable | disable]` commands now use the standard response author.

### Features

- Display `Content` on top of `Author` in `Quote sent` logging.
- Pinging the bot will now display a help message.
- `/fun` commands that had a delay now have a random delay.
- Added a public mod-log option.

## Version 0.6.0

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.5.2...v0.6.0)**

- Re-licensed to GPLv3.
- Added license headers to all files, and updated GitHub actions to account for that.
- Update Git structure; development for new versions now occurs on the `develop` branch, while stable releases are on
  the `release` branch.
- Removed an arbitrary limitation saying that you couldn't quote Pinguino.
- All responses now use a standardised embed format. This also applies to logging messages.

## Version 0.5.2

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.5.1...v0.5.2)**

- Created a dedicated `io.github.jamalam360.api` package for all the web APIs used by Pinguino.
- Cleaned up `ModuleExtension` by making registering enable/disable commands simple extensions functions.
- Randomise the profile pictures of `/quote non-user` quotes.
- There is now a config option (`/module moderation auto-save-threads`) that makes Pinguino automatically save all
  created threads
- The argument of `/thread save` now defaults to `true`
- Add documentation! Finally!
- Add `/moderation discipline unmute` to unmute members

## Version 0.5.1

**[Tag Comparison](https://github.com/JamCoreDiscord/Pinguino/compare/v0.5.0...v0.5.1)**

- Moved `UtilExtension` into two separate extensions, `ModeratorUtilityExtension` and
  `UserUtilityExtension`.
- Moved `Arguments.kt` into the `io.github.jamalam360.util` package.
- Made this changelog file.
- Improve the format of message edit and delete logging.
- Message delete logging now includes any attachments that were associated with the message.
- Stop using a deprecated KTor API.
- Bump all dependency versions
