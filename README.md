# Campus Lost & Found Matching System

A terminal-based Java (OOP) project. Students report lost items and found items,
and the system matches them up by category, location and keywords in the item name.
A background thread checks every new report for matches automatically.

## Features
- Report lost items (with an optional reward) and found items
- Valuable found items need a hidden detail to be claimed
- Search by item ID
- Matching that scores every lost/found pair (e.g. "86% match")
- Background Auto-Matcher thread that alerts you when a new item matches
- Save, load and back up items using files

## How to run
In Eclipse: right-click Main.java -> Run As -> Java Application.

From the terminal:
```
javac -d out src/*.java
java -cp out Main
```

## Project structure
| Class | Purpose |
|-------|---------|
| Main | Menu loop and input helpers |
| Item (abstract) | Base class for every item |
| LostItem, FoundItem, ValuableFoundItem | Item types (multilevel inheritance) |
| Matchable (interface) | Matching rule, implemented by LostItem |
| ItemStore | Array of items, thread-safe; matching, save, load, backup |
| AutoMatcher | Background thread (Runnable, wait/notifyAll) |
| ItemNotFoundException | Custom checked exception |

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
| 7 | 2026-10-27 | Cutting down excessive features (16 -> 9 files, 915 -> 463 lines) | Same OOP topics in a simpler design |
