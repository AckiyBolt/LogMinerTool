# LogMinerTool

This is simple tool for recursively search of appropriate lines in some log archive (directory tree).

Live test on: AMD A6-5400K@3.6GHz / 2.46RAM / Win7 / ~60 GB of logs
Was spent: ~30 min / ~50Mb RAM

Output is file that has format: [file_name] [founded_line].

Launch params:      [root_dir] [file_format] [looking_for] [out_file]
- **root_dir**    - start dir path, for recursively search.
- **file_format** - format of searchable files.
- **looking_for** - regular expression that matches searchable line.
                    ru man is here: http://www.quizful.net/post/Java-RegExp
- **out_file**    - output file path. If exist - will be deleted. Be careful.
   
Launch example (for *nix): java -jar logminer.jar "/user/anon/logs" ".log" "LoremIpsum" "/user/anon/out.txt"

If you want to see this help in live - just launch tool without any arguments
