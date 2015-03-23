# LogMinerTool

This is simple tool for recursively search of appropriate lines in some log archive (directory tree).

Output is file that has format: [file_name] [founded_line].

Search types:
- **contains**    - checks if line contains specific character sequence.
- **regexp**      - checks if line matches specific regexp.

###Performance tests
Contains search:
```
Live test on: AMD A6-5400K @3.6GHz / 2.46RAM / Win7 / Java 8 / ~60 GB of logs
Was spent: ~30 min / ~30Mb RAM
```
Regex search (oblivious things):
```
Runs twice slower if it was run with same request (one word).
If regex is like: '(one|two|three)' - search runs three times slower.
Also it eats almost all CPU time and twice more RAM.
```

###Launch
Launch params:      [root_dir] [file_format] [regex_flag] [looking_for] [out_file]
- **root_dir**    - start dir path, for recursively search.
- **file_format** - format of searchable files.
- **regex_flag**  - [y] - regex search. [n] - contains search. Flag isn't case sensitive.
- **looking_for** - searchable character sequence OR regular expression that matches searchable line.
                    RU manual about regex is here: http://www.quizful.net/post/Java-RegExp
- **out_file**    - output file path. If exist - will be deleted. Be careful.
   
Launch examples (for *nix):
```
java -jar logminer.jar "/user/anon/logs" ".log" "n" "LoremIpsum" "/user/anon/out.txt"
java -jar logminer.jar "/user/anon/logs" ".log" "y" "(Lorem|Ipsum|Dolor)" "/user/anon/out.txt"
```

If you want to see this help in live - just launch tool without any arguments
