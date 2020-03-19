# Description
This is a simple CLI based scholarship/student management system.
The requirements for this assignment were: 
- Use Sqlite
- Don't use an ORM framework
- Implement the main IO features demoed in this application (linking student<>scholarship was not necessary)

# How to run
This application depends on java 11, mvn, and make.. 
These programs must be installed and part of your path when running from the command line.
A simple Makefile was created for user convenience. <br>

To run, simply type: ```make run``` <br>

<b>Note:</b> Data is retained in a compiled folder. When ```make clean``` is utilized, data will be lost.

# Notes
The following implementation details assist in the simplicity of the application, however these may
be changed in future iterations.

- Linking students to scholarships has not yet been implemented

- There is no formatting validation for gpa, zipcode, price, etc. This means that a gpa of 5.5555 is acceptable.
A price of -100.222 is also considered acceptable.