# Litho Kotlin
Kotlin DSL for Litho ComponentLayout Builders.

# Usage

## Import
Just copy [`litho.kt`](https://raw.githubusercontent.com/vinc3m1/litho-kotlin/master/litho.kt) into your project under `com/makeramen/litho`. I may look into a proper Maven Central release in the future.

## Example
Root element must be a `layout`, this creates a `ComponentLayout` to be returned from an `@OnCreateLayout` function.

```java
  @OnCreateLayout
  fun onCreateLayout(c: ComponentContext): ComponentLayout = layout {
    column(c) {
      paddingDip(YogaEdge.ALL, 8f)
      children {
        text(c) {
          text("ONE")
          textSizeDip(16f)
        }
        text(c) {
          text("TWO")
          textSizeDip(16f)
        }
        text(c) {
          text("THREE")
          textSizeDip(16f)
        }
      }
    }
  }
```
