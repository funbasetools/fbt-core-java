# FunBaseTools core library for Java

[![Release](https://img.shields.io/github/v/release/funbasetools/fbt-core-java?include_prereleases&label=Release&logo=Github)](https://github.com/funbasetools/fbt-core-java/releases)
[![Build](https://img.shields.io/github/workflow/status/funbasetools/fbt-core-java/Build?label=Build&logo=Github)](https://github.com/funbasetools/fbt-core-java/releases)

FunBaseTools Core Lib provides a functional and multipurpose library for Java developers.

## How to install it

- Using maven:
```xml
<dependency>
    <groupId>com.funbasetools</groupId>
    <artifactId>fbt-core-java</artifactId>
    <version>latest</version>
</dependency>
```
- You also can download the latest sources from the release badge above.

## Features

The library has a set of useful features to apply functional programming to Java projects.
Below you can see some utilities:

### Lazy Evaluation
```java
import com.funbasetools.Lazy;

class MyClass {

    // This field value will be computed only when used the first time
    private final Lazy<String> lazyField = Lazy.of(() -> {

        // Perform a very complicated and big computation
        return "This result will be computed only the first time";
    });

    public String getResult() {
        return lazyField.get();
    }
}
```

### Streams
```java
import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;

class MyClass {

    // natural numbers stream
    private final Stream<Integer> naturals = Streams.from(1);

    // factorial stream
    private final Stream<Integer> factorial = getFactorial();

    // fibonacci stream
    private final Stream<Integer> fibonacci = getFibonacci();
    
    public void testNaturals() {
        final Stream<Integer> evens = naturals.filter(i -> i % 2 == 0);
        final Stream<Integer> odds = naturals.filter(i -> i % 2 != 0);

        assert evens.corresponds(Streams.of(2, 4, 6, 8, 10, 12, 14, 16, 18, 20));
        assert odds.corresponds(Streams.of(1, 3, 5, 7, 9, 11, 13, 15, 17, 19));
    }
    
    public void testFactorial() {
        assert factorial
                .corresponds(Streams.of(1, 2, 6, 24, 120, 720, 5040, 40320, 362880));
    }
    
    public void testFibonacci() {
        assert factorialStream
                .corresponds(Streams.of(1, 1, 2, 3, 5, 8, 13, 21, 34));
    }

    // private methods    

    /**
    /* Creates a stream of pairs with the current fibonacci value and the next natural number, 
    /* after multiplying the pair, the result is the next factorial value.
    /* Awesome!!!
    */
    private Stream<Integer> getFactorial() {
        return Streams
                .of(1, () -> factorialStream
                    .zip(naturalStream.getTail())
                    .map(pair -> pair.getRight() * pair.getLeft())
                );
    }

    /**
    /* Creates a stream of pairs with the current and next fibonacci values and then adds the pair values.
    /* The result is the next fibonacci number.
    /* Wonderful right?
    */
    private Stream<Integer> getFibonacci() {
        return Streams
                .of(1, 1)
                .append(() -> fibonacci
                    .zip(fibonacci.getTail())
                    .map(pair -> pair.getLeft() + pair.getRight())
                );
    }
}
```

## And more...

Also, you can find:

- Try, Success and Failures monads
- Function, Supply and Consumer extended types and their throwing exception similar
- Codecs
- Basic security API

Take a look to the unit tests of the library for more use cases and examples.

-----------------------------------

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://paypal.me/RobertoMartinezB)
