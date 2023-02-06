<h1>Encrypting Properties in Spring Boot with Jasypt</h1>
<br>
Jasypt is a library that provides simple encryption and decryption of configuration properties in Java-based applications. In this guide, we will show you how to use Jasypt to encrypt properties in a Spring Boot application.
<br>
<h2>Step 1: Add Jasypt Dependency</h2>
First, add the Jasypt library to your project by adding the following dependency to your <code>'build.gradle'</code> file:
<br><br>
<code>implementation "com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5"</code>
<br><br>
<h2>Step 2: Encrypt Properties</h2>
Next, encrypt the properties you want to keep secure. You can use the Jasypt CLI tool to encrypt the values of your properties. To do this, you'll need to download the Jasypt distribution, extract the contents, and then run the encrypt.sh script.
<br><br>
For example, to encrypt the value of a password property, run the following command:

<code>
<br>
sudo /bin/bash ./encrypt.sh input="your_password" password=jasypt_encryption_password algorithm=PBEWithMD5AndDES 
</code>
<br>
<pre>
<code>
----ENVIRONMENT-----------------

Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 17.0.4.1+1-LTS-2

----ARGUMENTS-------------------

input: your_password
password: jasypt_encryption_password
algorithm: PBEWithMD5AndDES

----OUTPUT----------------------

wBXj2dveGGQVppo0pPN+8xnSonOF5b6q

</code>
</pre>

Note that jasypt_encryption_password is the encryption password that you will use to encrypt and decrypt your properties, and PBEWithMD5AndDES is the encryption algorithm you will use.
<br><br>
The output of the command will be an encrypted string, which you can then use as the value for your password property in your <b>'application.yml'</b> file.
<br><br>
<h2>Step 3: Update 'application.yml'</h2>
Now that you have encrypted your properties, you can update your <b>'application.yml'</b> file with the encrypted values. For example:
<br>
<code>your_property: ENC(encrypted_value)</code>
<br><br>
where <b>'encrypted_value'</b> is the encrypted value you obtained in step 2.
<br><br>
<h2>Step 4: Decrypt Properties in Spring Boot</h2>
Finally, to decrypt the properties in your Spring Boot application, you simply need to add the following configuration to your <b>'application.yml'</b> file:
<pre>
<code>
jasypt:
    encryptor:
        password: jasypt_encryption_password
</code>
</pre>
<br>
we can also set this value with system variable
<code>
JASYPT_ENCRYPTOR_PASSWORD=mypassword
</code>

where <b>'jasypt_encryption_password'</b> is the encryption password you used in step 2.
<br><br>
And that's it! Your properties are now encrypted and decrypted automatically in your Spring Boot application.
<br><br>
<h2>Conclusion</h2>
In this guide, we showed you how to encrypt properties in a Spring Boot application using Jasypt. With just a few simple steps, you can now keep your sensitive information secure and easily manage it in your application.

<h3>To run the application:</h3>
<code>docker-compose up</code>
<br>
or
<br>
<code>java -jar property-encryption-with-jasypt-0.0.1.jar --jasypt.encryptor.password=password</code>
