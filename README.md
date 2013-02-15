FuzzerProject
=============

To get started:

You will need:
-Java
-Maven
-Eclipse (recommended)

1.  Clone into your eclipse workspace.
2.  Import it into Eclipse as an existing Maven project

=============

To ensure the correctness of the fuzzer's sensitive data list, prior to 
running the fuzzer be sure that you have registered with the targeted 
web applications with the following registration information:

DVWA: (use default login information)

Username:	admin
Password:	password

-------

JPetStore:

User ID: 	njf1116	
Password: 	genericpassword
First Name: Nick
Last Name:	Fuschino
Email:		njfuschino@website.com
Phone:		555-555-5555
Address 1:	123 Fake Street

-------

BodgeIt:

Username:	nick@website.com
Password:	genericpassword



=============

To run the Fuzzer from the command line:

"java Fuzzer 0 1 2 3 4 5 6"

	Arguments:
		0 : target URL to fuzz
		1 : time delay between requests (can be 0)
		2 : filepath to text file containing sensitive data
		3 : filepath to text file containing malicious/sanitizable input
		4 : filepath to text file containing pages to guess
		5 : filepath to text file containing user names 
		6 : filepath to text file containing passwords to guess