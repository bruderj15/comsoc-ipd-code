# Strategies in the Iterated Prisoners' Dilemma (OS Computational Social Choice HTWK Leipzig Wi-Se 25/26)

Demo code for lecture on the iterative prisoners dilemma in advanced seminar "Computational Social Choice"

## Execute

We will run the application by using the supplied [Maven-Wrapper](https://mvnrepository.com/artifact/org.apache.maven.wrapper/maven-wrapper-parent).

### Unix

```bash
./mvnw spring-boot:run
```

### Windows PowerShell

```bash
.\mvnw spring-boot:run
```

## Contribute

You can contribute by implementing strategies for the iterated prisoners' dilemma.

Add a new class and implement `Strategy`:

```java
import com.example.demo.Choice;

public class Foo implements Strategy {
    @Override
    public Choice initial() {
        return Choice.DEFECT;
    }

    @Override
    public Choice move(Choice myLastChoice, Choice opponentLastChoice) {
        return Choice.COOPERATE;
    }
}
```

Annotate the class with `@Component` to make it discoverable via classpath-scanning using SpringBoot:

```java
import com.example.demo.Choice;
import org.springframework.stereotype.Component;

@Component
public class Foo implements Strategy {
    // ...
}
```

That's it! Your strategy will be picked up and played against all other strategies.