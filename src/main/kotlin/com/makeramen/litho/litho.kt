/*
 * Copyright (C) 2017 Vincent Mi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makeramen.litho

import com.facebook.litho.*
import com.facebook.litho.widget.*

@DslMarker
annotation class LithoMarker

/** A concrete class to help scope the DSL. */
class ChildHolder {
  val children = mutableListOf<Any>()
}

/**
 * Root wrapper to return a ComponentLayout.
 * @param init the DSL builder lambda. Only one child is allowed in a layout{}.
 */
fun layout(init: ChildHolder.() -> Unit): ComponentLayout {
  // Bit of a compromise to maintain recursive type safety.
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

/**
 * Base implementation for containers (row and column).
 * We have to differentiate here because Litho's Builder and ContainerBuilder types are different.
 * @param c the {@link ComponentContext} needed to create this builder.
 * @param create the creator function reference. E.g. Row::create
 * @param init the DSL builder lambda.
 */
private fun ChildHolder.container(
    c: ComponentContext,
    create: (c: ComponentContext) -> ComponentLayout.ContainerBuilder,
    init: ComponentLayout.ContainerBuilder.() -> Unit) {
  val builder = create(c)
  builder.init()
  this.children.add(builder)
}

/**
 * Base implementation from components (non containers).
 * We have to differentiate here because Litho's Builder and ContainerBuilder types are different.
 * @param c the {@link ComponentContext} needed to create this builder.
 * @param create the creator function reference. E.g. Text::create.
 * @param init the DSL builder lambda.
 */
private fun <C : Component<out Component<*>>, B : Component.Builder<C, B>> ChildHolder.component(
    c: ComponentContext,
    create: (c: ComponentContext) -> B,
    init: B.() -> Unit) {
  val builder = create(c)
  builder.init()
  this.children.add(builder)
}

/**
 * A DSL element to wrap children and compiles to .child() calls on the parent.
 * @param init the DSL builder lambda to add children to.
 */
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

/** Containers */

@LithoMarker
fun ChildHolder.column(
    c: ComponentContext,
    init: ComponentLayout.ContainerBuilder.() -> Unit) = container(c, Column::create, init)

@LithoMarker
fun ChildHolder.row(
    c: ComponentContext,
    init: ComponentLayout.ContainerBuilder.() -> Unit) = container(c, Row::create, init)

/** Components */

@LithoMarker
fun ChildHolder.card(c: ComponentContext, init: Card.Builder.() -> Unit) =
    component(c, Card::create, init)

@LithoMarker
fun ChildHolder.editText(c: ComponentContext, init: EditText.Builder.() -> Unit) =
    component(c, EditText::create, init)

@LithoMarker
fun ChildHolder.horizontalScroll(c: ComponentContext, init: HorizontalScroll.Builder.() -> Unit) =
    component(c, HorizontalScroll::create, init)

@LithoMarker
fun ChildHolder.image(c: ComponentContext, init: Image.Builder.() -> Unit) =
    component(c, Image::create, init)

@LithoMarker
fun ChildHolder.recycler(c: ComponentContext, init: Recycler.Builder.() -> Unit) =
    component(c, Recycler::create, init)

@LithoMarker
fun ChildHolder.solidColor(c: ComponentContext, init: SolidColor.Builder.() -> Unit) =
    component(c, SolidColor::create, init)

@LithoMarker
fun ChildHolder.text(c: ComponentContext, init: Text.Builder.() -> Unit) =
    component(c, Text::create, init)

@LithoMarker
fun ChildHolder.verticalScroll(c: ComponentContext, init: VerticalScroll.Builder.() -> Unit) =
    component(c, VerticalScroll::create, init)
