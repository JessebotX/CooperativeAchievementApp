# Clean & Simple Code

```java
// Option 1:
final String HIGHEST_PRIORITY = "clean code";
System.out.println("My priority: " + HIGHEST_PRIORITY + ".");

// Option 2:
char[]w="\0rd0ct33lri".toCharArray();
for(int x=0xA;w[x]>0;System.out.print(w[x--]));
```

# Use Meaningful Names
- All identifiers must have meaningful, human-readable, English names. Avoid cryptic abbreviations such as dspl(), cntStd(), or stdRegYYYYMMDD. Instead use:
- Exception 1: loop variables may be i, j or k. However, prefer the for-each loop when possible.
- Exception 2: variables with very limited scope (<20 lines) may be shortened if the purpose of the variable is clear.

```java
void display();
int countStudents();
Date dateStudentRegistered;
```

# Naming Conventions
- Constants must be all upper case, with multiple words separated by '_':
- Functions must start with a lower case letter, and use CamelCase. Functions should be named in terms of an action:
- Class names must start with an upper case letter, and use CamelCase. Classes should be named in terms of a singular noun:
  Constant should have the most restrictive scope possible. For example, if it is used in only one class, then define the constant as private to that class. If a constant is needed in multiple classes, make it public.
- Generally favour local variables over "more global" variables such as class fields. In almost all languages, global variables are terrible!
- Do not use prefixes for variables: don't encode the type (like iSomeNumber, or strName), do not prefix member variables of a class have with m_.

```java
if (isOpen) {
    ...
}
while (!isEndOfFile && hasMoreData()) {
    ...
}
```

# Indentation and Braces {...}
- There's always time for perfect indentation.
- Tab size is 4; indentation size is 4. Use tabs to indent code.

```java
if (j < 10) {
→   counter = getStudentCount(lowIndex,
→   →   highIndex);
→   if (x == 0) {
→   →   if (y != 0) {
→   →   →   x = y;→   →   // Insightful comment here
→   →   }
→   }
}
```

- Exception: if statements with multi-line conditions have the starting brace aligned on the left to make it easier to spot the block in the if statement.
- All if statements and loops should include braces around their statements, even if there is only one statement in the body:

# Statements and Spacing

- Declare each variable in its own definition, rather than together (int i, j).
- All binary (2 argument) operators (arithmetic, bitwise and assignment) and ternary conditionals (?:) must be surrounded by one space. Commas must have one space after them and none before. Unary operators (!, *, &, - (ex: -1), + (ex: +1), ++, --) have no additional space on either side of the operator.
- Each statement should be on its own line:

```java
// Good:
i = j + k;
l = m * 2;

// Bad (what are you hiding?):
if (i == j) l = m * 2;
    cout << "Can ya read this?" << endl;
```


# Classes
Inside a class, the fields must be at the top of the class, followed by the methods.

```java
class Pizza {
    private int toppingCount;

    public Pizza() {
        toppingCount = 0;
    }
    public int getToppingCount() {
        return toppingCount;
    }
    ...
}

class Topping {
    private String name;
    ...
    public String getName() {
        return name;
    }
}
```

# Comments
- Comments which are on one line should use the // style. Comments which are or a couple lines may use either the //, or /* ... */ style. Comments which are many lines long should use /* ... */.
- Each class must have a descriptive comment before it describing the general purpose of the class. These comments should be in the JavaDoc format. Recommended format is shown below:

```java
/**
 * Student class models the information about a 
 * university student. Data includes student number, 
 * name, and address. It supports reading in from a 
 * file, and writing out to a file.
 */
class Student {
    ...
}
```


# Comments vs Functions
- our code should not need many comments. Generally, before writing a comment, consider how you can refactor your code to remove the need to "freshen" it up with a comment.
- When you do write a comment, it must describe why something is done, not what it does:

```java
// Cast to char to avoid runtime error for 
// international characters when running on Windows 
if (isAlpha((char)someLetter)) {
    ...
}
```


# Other
- Either post-increment or pre-increment may be used on its own:

```java
i++;
++j;

```

- Avoid using goto. When clear, design your loops to not require the use of break or continue.
- All switch statements should include a default label. If the default case seems impossible, place an assert false; in it. Comment any intentional fall-throughs in switch statements:

```java
switch(buttonChoice) {
case YES:
    // Fall through
case OK:
    System.out.println("It's all good.");
    break;
case CANCEL:
    System.out.println("It's over!");
    break;
default:
    assert false;
}
```

- Use plenty of assertions. Any time you can use an assertion to check that some condition is true which "must" be true, you can catch a bug early in development. It is especially useful to verify function pre-conditions for input arguments or the object's state. Note that you must give the JVM the -ea argument (enable assertions) for it to correctly give an error message when an assertion fails.
- Never let an assert have a side effect such as assert i++ > 1;. This may do what you expect during debugging, but when you build a release version, the asserts are removed. Therefore, the i++ won't happen in the release build.