package com.makeramen.litho

import com.facebook.litho.*
import com.facebook.litho.widget.*

@DslMarker
annotation class LithoMarker

fun layout(init: ChildHolder.() -> Unit): ComponentLayout {
  val childHolder = ChildHolder()
  childHolder.init()
  assert(childHolder.children.size == 1) { "layout must have exactly one child." }
  val child = childHolder.children[0]
  return when (child) {
    is ComponentLayout.Builder -> {
      child.build()
    }
    is Component.Builder<*, *> -> {
      child.buildWithLayout()
    }
    else -> {
      throw IllegalArgumentException("layout element must be Component.Builder or ComponentLayout.Builder.")
    }
  }
}

@LithoMarker
fun ChildHolder.column(c: ComponentContext,
                                           init: ComponentLayout.ContainerBuilder.() -> Unit) = container(c, Column::create, init)

@LithoMarker
fun ChildHolder.row(c: ComponentContext,
                                        init: ComponentLayout.ContainerBuilder.() -> Unit) = container(c, Row::create, init)

private fun ChildHolder.container(
    c: ComponentContext,
    create: (c: ComponentContext) -> ComponentLayout.ContainerBuilder,
    init: ComponentLayout.ContainerBuilder.() -> Unit) {
  val builder = create(c)
  builder.init()
  this.children.add(builder)
}

@LithoMarker
fun ChildHolder.card(c: ComponentContext, init: Card.Builder.() -> Unit) =
    componentBuilder(c, Card::create, init)

@LithoMarker
fun ChildHolder.editText(c: ComponentContext, init: EditText.Builder.() -> Unit) =
    componentBuilder(c, EditText::create, init)

@LithoMarker
fun ChildHolder.horizontalScroll(c: ComponentContext, init: HorizontalScroll.Builder.() -> Unit) =
    componentBuilder(c, HorizontalScroll::create, init)

@LithoMarker
fun ChildHolder.image(c: ComponentContext, init: Image.Builder.() -> Unit) =
    componentBuilder(c, Image::create, init)

@LithoMarker
fun ChildHolder.recycler(c: ComponentContext, init: Recycler.Builder.() -> Unit) =
    componentBuilder(c, Recycler::create, init)

@LithoMarker
fun ChildHolder.solidColor(c: ComponentContext, init: SolidColor.Builder.() -> Unit) =
    componentBuilder(c, SolidColor::create, init)

@LithoMarker
fun ChildHolder.text(c: ComponentContext, init: Text.Builder.() -> Unit) =
    componentBuilder(c, Text::create, init)

@LithoMarker
fun ChildHolder.verticalScroll(c: ComponentContext, init: VerticalScroll.Builder.() -> Unit) =
    componentBuilder(c, VerticalScroll::create, init)

@LithoMarker
private fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> ChildHolder.componentBuilder(
    c: ComponentContext,
    create: (c: ComponentContext) -> B,
    init: B.() -> Unit) {
  val builder = create(c)
  builder.init()
  this.children.add(builder)
}

@LithoMarker
fun ComponentLayout.ContainerBuilder.children(init: ChildHolder.() -> Unit) {
  val childHolder = ChildHolder()
  childHolder.init()
  for (child in childHolder.children) {
    when (child) {
      is ComponentLayout -> {
        this.child(child)
      }
      is ComponentLayout.Builder -> {
        this.child(child)
      }
      is Component<*> -> {
        this.child(child)
      }
      is Component.Builder<*, *> -> {
        this.child(child)
      }
    }
  }
}

class ChildHolder {
  val children = mutableListOf<Any>()
}
