# Litho Kotlin
Kotlin DSL for [Litho](https://fblitho.com) `Component.Builder`s.

# Usage

## Import
1. Copy [`Litho.kt`](https://raw.githubusercontent.com/vinc3m1/litho-kotlin/master/src/main/kotlin/com/makeramen/litho/Litho.kt) into your project under `com/makeramen/litho`.
2. Add any definitions for your own custom components, e.g.
    ```kotlin
    @LithoMarker
    fun myComponent(c: ComponentContext, init: MyComponent.Builder.() -> Unit) =
        componentBuilder(c, MyComponent::create, init)

    @LithoMarker
    fun ChildHolder.myComponent(c: ComponentContext, init: MyComponent.Builder.() -> Unit) =
        componentBuilder(c, MyComponent::create, init)
    ```
3. Remove any definitions you don't want to use, like `recyclerCollectionComponent` if you're not using [Sections](https://fblitho.com/docs/sections-intro).

I may look into a proper Maven Central release in the future, but it's just one file, and extensibility for custom components is the best part.

## Example

```kotlin
@LayoutSpec
object ExampleComponentSpec {

  @OnCreateLayout
  fun onCreateLayout(c: ComponentContext): Component {
    return column(c) {               // Init root Components by passing in the context
      paddingDip(YogaEdge.ALL, 8f)   // Attributes can be defined inline within the lambda
      children {                     // Add children using a children element
        text {                       // ComponentBuilders within a children element don't need a context
          text("TWO")
          textSizeDip(16f)
        }
        text {
          text("THREE")
          textSizeDip(16f)
        }
      }
    }
  }
}
```
