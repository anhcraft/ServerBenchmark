# ServerBenchmark

simple Minecraft server benchmark tool

Commands:
- `/bench entity start <entity type> <amount> <interval in seconds> <tps min>`: starts a schedule to repeatedly spawn `entity type` for every `interval` seconds, and automatically end if the TPS is below `tps min`
- `/bench entity end`

You may utilize other tools to inspect the performance:
- spark
- built-in timings
- heap dump + viewer tool
