


# jOOQ 3.15 Drop Java 6/7 support for Enterprise Edition, require Java 11 in OSS Edition

We're cleaning up our support for old dependencies and features. 
Starting with jOOQ 3.12, we offered Java 6 and 7 support only to jOOQ Enterprise Edition customers. 
With jOOQ 3.15, this support has now been removed, and Java 8 is the new baseline for commercial editions, 
Java 11 for the jOOQ Open Source Edition, meaning the OSS Edition is now finally modular, 
and we get access to little things like the Flow API (see R2DBC) and @Deprecate(forRemoval, since).

