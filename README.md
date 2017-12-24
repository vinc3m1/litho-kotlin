# Litho Kotlin
Kotlin DSL for Litho ComponentLayout Builders.

# Usage

## Import
Just copy [`litho.kt`](https://raw.githubusercontent.com/vinc3m1/litho-kotlin/master/src/main/kotlin/com/makeramen/litho/litho.kt) into your project under `com/makeramen/litho`. I may look into a proper Maven Central release in the future.

## Example

```kotlin
@LayoutSpec
object ExampleComponentSpec {

  @OnCreateLayout
  fun onCreateLayout(c: ComponentContext): ComponentLayout = layout { // Root element must be a layout, returns a ComponentLayout
    column(c) { // Init builders by passing in the context
      paddingDip(YogaEdge.ALL, 8f) // Attributes can be defined inline within the lambda
      children { // Children can be added through a children element
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
}
```
