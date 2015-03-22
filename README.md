# LogMinerTool

This is simple tool for recursively search of appropriate lines in some log archive (directory tree).

Output is file that has format: [file_name] [founded_line].

Launch params:      [root_dir] [file_format] [looking_for] [out_file]
- **root_dir**    - start dir path, for recursively search.
- **file_format** - format of searchable files.
- **looking_for** - something that log line should contains.
- **out_file**    - output file path. If exist - will be deleted. Be careful.
   
Launch example (for *nix): java -jar logminer.jar "/user/anon/logs" ".log" "LoremIpsum" "/user/anon/out.txt"

If you want to see this help in live - just launch tool without any arguments
