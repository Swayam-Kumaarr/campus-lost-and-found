9# Campus Lost & Found Matching System

A terminal-based Java (OOP) project. Students report lost items and found items,
and the system matches them up (by category, location, date, keywords).

Built incrementally, one small step every 1–2 days, so every line can be explained.

## How to run
```
javac -d out src/*.java
java -cp out Main
```

## Progress log
| Day | Date | What was added | Java concept |
|-----|------|----------------|--------------|
| 0 | 2026-10-16 | Repository setup, README | - |
| 1 | 2026-10-16 | Main menu loop | Scanner, switch, do-while |
| 2 | 2026-10-22 | Item hierarchy and Student | Classes, constructors, inheritance, abstract, final, composition |
| 3 | 2026-10-22 | ItemStore and working menu | Arrays, objects as parameters, polymorphism |
| 4 | 2026-10-22 | Matching engine | Interfaces, recursion, nested class, overloading |
| 5 | 2026-10-22 | Exceptions, input validation, files | Custom exceptions, try/catch/finally, wrapper classes, file and byte streams |
| 6 | 2026-10-22 | Background threads | Runnable vs Thread, synchronized, wait/notifyAll, join, GC |
| 7 | 2026-10-22 | PostgreSQL JDBC driver added to lib | External library (for future online storage) |
| 8 | 2026-10-23 | Category report and admin login | 2D arrays, printf, packages, access control |
