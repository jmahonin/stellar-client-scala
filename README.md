# Stellar Scala Client

After reading through some of the JS crypto used in the Stellar project 
(http://stellar.org), I wanted to see what the end result would look like
re-implemented in Scala using JVM crypto libraries.

Details:
- AES-GCM decryption using Bouncy Castle
- Scrypt using lambdaworks implementation
- Requires JDK 7
- Only wallet decryption supported

Can be run from the command line using:
`sbt "run <username> <password>"`

Note: Use at your own risk. Even though the your username and password are
first before transmission over SSL, I make no guarantees about the correctness
of this code or the safety of your credentials.

Donations welcome to username: jmahonin