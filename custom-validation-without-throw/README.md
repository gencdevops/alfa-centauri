<h1>Custom Validation in Spring</h1>
<p>Spring supports Bean Validation API's for performing validation operations on Java Beans. The Bean Validation API's use the JSR-303 and JSR-349 standards. However, sometimes the application's needs require more control than the Bean Validation API's can provide. Therefore, Spring offers a mechanism that you can use to perform custom validation.</p>

<br>
<br>
<h3>Creating Custom Validation Class</h3>
<p>To create your custom validation class, it should be a class that implements the <code>javax.validation.ConstraintValidator</code> interface. This class should contain the <code>initialize</code> and <code>isValid</code> methods.</p>
<li>The <code>initialize</code> method is used to determine which variables your class will validate and which parameters it will use.</li>
<li>The <code>isValid</code> method is used to check whether a specific value is valid or not.</li>

<br>
<br>
<h3>Using Custom Validation Class</h3>
<p>You can use your custom validation class to validate relevant variables in your Java Bean. To use the custom validation class, you need to mark your class with the <code>@Constraint</code> annotation. This annotation is used to determine which variables your class will validate and which error message to display.</p>

<div class="p-4 overflow-y-auto">
<pre>
<code>
@Constraint(validatedBy = MyCustomValidator.class)
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface MyCustomConstraint {
    String message() default "Invalid value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
</code>
</pre>
</div>

<br>
<br>
<h3>Using Custom Constraints Within Bean</h3>
<p>Once you have your custom validation class and have marked it, you can use it for the relevant variables in your Java Bean. For example, let's say you have the following Java Bean class:</p>

<div class="p-4 overflow-y-auto">
<pre>
<code>
public class MyBean {
    @MyCustomConstraint
    String name;
    //...
}
</code>
</pre>
</div>

<p>This Bean class will use the <code>MyCustomValidator</code> class to perform validation on the name variable.</p>

<br>
<br>
<h3>Configuring Error Messages</h3>
<p>If your custom validation class returns an invalid value, Spring will automatically display an error message. You can configure what that error message will be by using the message property. </p>

<br>
<h2>Summary</h2>
<p>Spring supports Bean Validation API's for performing validation operations on Java Beans. However, sometimes the application's needs require more control than the Bean Validation API's can provide. Therefore, Spring offers a mechanism that you can use to perform custom validation. By creating your custom validation class and applying it to your Bean class, Spring will automatically perform the validation process.</p>
<br>
<br>
<h3>To run the application:</h3>
<code>docker-compose up</code>