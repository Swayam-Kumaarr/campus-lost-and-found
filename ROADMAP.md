# Roadmap — Campus Lost & Found Matching System

Course: Object Oriented Programming (CSC2104), Manipal University Jaipur, Jul–Nov 2026.
Plan: one small step every ~2 days, from 17 Oct to 30 Nov 2026.
Every step maps to a lecture topic from the course handout, so the project covers the whole syllabus.

## The finished program (what we are building toward)
A terminal menu where a student can:
- report a **lost** item (what, category, where, when, contact)
- report a **found** item
- view or search all items
- run **matching**, which compares lost and found items and gives each pair a score
- save items to a file and load them again when the program restarts
- leave a **background thread** running that checks for new matches automatically

## Session plan

| # | Target date | What we build | Syllabus topics (lecture no.) |
|---|-------------|---------------|-------------------------------|
| 1 | 17 Oct | Main menu loop in `Main.java` | Datatypes, operators, if/switch, loops, console input with Scanner (3–6, 32) |
| 2 | 19 Oct | `Item` class: fields, constructor, `toString()` | Classes, objects, methods, constructors (10, 12, 13) |
| 3 | 21 Oct | Encapsulation: `private` fields, getters/setters, `this`, auto-generated IDs with `static` | Access control, `final` (18, 19) |
| 4 | 23 Oct | `ItemStore` that keeps items in an **array**; add + list items from the menu | Arrays, objects as parameters (9, 15) |
| 5 | 25 Oct | Search by category, by location, by keyword | Method overloading, returning objects (14, 17) |
| 6 | 27 Oct | Safe number input, dates stored as numbers | Type conversion, wrapper classes, autoboxing (8) |
| 7 | 29 Oct | `abstract class Item` → `LostItem`, `FoundItem` | Inheritance, `extends`, `super`, abstract classes (22, 24, 30) |
| 8 | 31 Oct | `ValuableFoundItem extends FoundItem` (phones/wallets need ID check) | Multilevel hierarchy, `protected` (23, 26) |
| 9 | 2 Nov | One `Item[]` array holding all types, each printing itself differently | Method overriding, runtime polymorphism (27, 28) |
| 10 | 4 Nov | `interface Matcher` + `MatchEngine` that scores lost/found pairs | Interfaces (30) |
| 11 | 6 Nov | Recursive keyword comparison in the matcher | Recursion (17) |
| 12 | 8 Nov | `MatchEngine.MatchResult` static nested class; an inner class in the menu | Nested and inner classes (21) |
| 13 | 10 Nov | `Student` class that **has** items it reported | Inheritance vs. composition (31) |
| 14 | 12 Nov | `InvalidInputException`, `ItemNotFoundException` | Exception types, try/catch/finally, throw/throws (36–38) |
| 15 | 14 Nov | Save/load items to `items.txt` | FileWriter, FileReader/BufferedReader (33, 39) |
| 16 | 16 Nov | Backup copy of the data file | Byte streams: FileInputStream/FileOutputStream (35) |
| 17 | 18 Nov | `AutoMatcher` background thread | Creating threads: Thread vs. Runnable, start/run, join, priorities (40–43) |
| 18 | 20 Nov | Make `ItemStore` thread-safe | `synchronized` methods and blocks (45) |
| 19 | 22 Nov | New reports wake up the matcher | wait(), notify(), notifyAll() (46) |
| 20 | 24 Nov | Pause/resume/stop the matcher from the menu | Suspending, resuming, stopping threads with flags (47) |
| 21 | 26 Nov | Mark items "claimed" and drop references | Garbage collection, `final` review (19) |
| 22 | 28–30 Nov | Clean-up, final README, viva practice questions | Revision (49–51) |

If you fall behind, sessions 11, 16 and 21 are the easiest to merge into a neighbouring session.

## Rules we follow every session
1. Small change, usually 20–60 lines.
2. Every new line has a comment or a note in `docs/day-XX.md` saying what it does and why.
3. Compile and run before committing.
4. Commit message format: `Day N: <what changed> (<concept>)`.
5. Add a row to the progress log in `README.md`.
