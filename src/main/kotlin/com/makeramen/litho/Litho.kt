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
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.*

/** A concrete class to help scope the DSL. */
class ChildHolder(private val context: ComponentContext) {
  private val children = mutableListOf<Component.Builder<*>>()

  fun <B : Component.ContainerBuilder<B>> apply(containerBuilder: Component.ContainerBuilder<B>) {
    children.forEach { containerBuilder.child(it) }
  }

  /**
   * Base implementation for components as children of containers.
   * @param create the creator function reference. E.g. Text::create.
   * @param init the DSL builder lambda.
   */
  private fun <B : Component.Builder<B>> componentBuilder(
      create: (c: ComponentContext) -> B,
      init: B.() -> Unit) {
    val builder = create(context)
    builder.init()
    children.add(builder)
  }

  fun column(init: Column.Builder.() -> Unit) = componentBuilder(Column::create, init)
  fun row(init: Row.Builder.() -> Unit) = componentBuilder(Row::create, init)
  fun card(init: Card.Builder.() -> Unit) = componentBuilder(Card::create, init)
  fun cardClip(init: CardClip.Builder.() -> Unit) = componentBuilder(CardClip::create, init)
  fun editText(init: EditText.Builder.() -> Unit) = componentBuilder(EditText::create, init)
  fun empty(init: EmptyComponent.Builder.() -> Unit) = componentBuilder(EmptyComponent::create, init)
  fun horizontalScroll(init: HorizontalScroll.Builder.() -> Unit) = componentBuilder(HorizontalScroll::create, init)
  fun image(init: Image.Builder.() -> Unit) = componentBuilder(Image::create, init)
  fun progress(init: Progress.Builder.() -> Unit) = componentBuilder(Progress::create, init)
  fun lazySelector(init: LazySelectorComponent.Builder.() -> Unit) = componentBuilder(LazySelectorComponent::create, init)
  fun recycler(init: Recycler.Builder.() -> Unit) = componentBuilder(Recycler::create, init)
  fun recyclerCollectionComponent(init: RecyclerCollectionComponent.Builder.() -> Unit) = componentBuilder(RecyclerCollectionComponent::create, init)
  fun selector(init: SelectorComponent.Builder.() -> Unit) = componentBuilder(SelectorComponent::create, init)
  fun solidColor(init: SolidColor.Builder.() -> Unit) = componentBuilder(SolidColor::create, init)
  fun text(init: Text.Builder.() -> Unit) = componentBuilder(Text::create, init)
  fun verticalScroll(init: VerticalScroll.Builder.() -> Unit) = componentBuilder(VerticalScroll::create, init)
}

/**
 * Base implementation for components.
 * @param c the {@link ComponentContext} needed to create this builder.
 * @param create the creator function reference. E.g. Text::create.
 * @param init the DSL builder lambda.
 */
fun <B : Component.Builder<B>> component(
    c: ComponentContext,
    create: (c: ComponentContext) -> B,
    init: B.() -> Unit): Component {
  val builder = create(c)
  builder.init()
  return builder.build()
}

/**
 * Base implementation for components.
 * @param c the {@link ComponentContext} needed to create this builder.
 * @param create the creator function reference. E.g. Text::create.
 * @param init the DSL builder lambda.
 */
fun <B : Component.Builder<B>> componentBuilder(
    c: ComponentContext,
    create: (c: ComponentContext) -> B,
    init: B.() -> Unit): Component.Builder<B> {
  val builder = create(c)
  builder.init()
  return builder
}

/**
 * A DSL element to wrap children and compiles to .child() calls on the parent.
 * @param init the DSL builder lambda to add children to.
 */
fun <B : Component.ContainerBuilder<B>> Component.ContainerBuilder<B>.children(
    init: ChildHolder.() -> Unit) {
  val childHolder = ChildHolder(context!!)
  childHolder.init()
  childHolder.apply(this)
}

/** Containers */
fun column(c: ComponentContext, init: Column.Builder.() -> Unit) = component(c, Column::create, init)

fun row(c: ComponentContext, init: Row.Builder.() -> Unit) = component(c, Row::create, init)

/** Components */
fun card(c: ComponentContext, init: Card.Builder.() -> Unit) = componentBuilder(c, Card::create, init)

fun cardClip(c: ComponentContext, init: CardClip.Builder.() -> Unit) = componentBuilder(c, CardClip::create, init)
fun editText(c: ComponentContext, init: EditText.Builder.() -> Unit) = componentBuilder(c, EditText::create, init)
fun empty(c: ComponentContext, init: EmptyComponent.Builder.() -> Unit) = componentBuilder(c, EmptyComponent::create, init)
fun horizontalScroll(c: ComponentContext, init: HorizontalScroll.Builder.() -> Unit) = componentBuilder(c, HorizontalScroll::create, init)
fun image(c: ComponentContext, init: Image.Builder.() -> Unit) = componentBuilder(c, Image::create, init)
fun lazySelector(c: ComponentContext, init: LazySelectorComponent.Builder.() -> Unit) = componentBuilder(c, LazySelectorComponent::create, init)
fun progress(c: ComponentContext, init: Progress.Builder.() -> Unit) = componentBuilder(c, Progress::create, init)
fun recycler(c: ComponentContext, init: Recycler.Builder.() -> Unit) = componentBuilder(c, Recycler::create, init)
fun recyclerCollectionComponent(c: ComponentContext, init: RecyclerCollectionComponent.Builder.() -> Unit) = componentBuilder(c, RecyclerCollectionComponent::create, init)
fun selector(c: ComponentContext, init: SelectorComponent.Builder.() -> Unit) = componentBuilder(c, SelectorComponent::create, init)
fun solidColor(c: ComponentContext, init: SolidColor.Builder.() -> Unit) = componentBuilder(c, SolidColor::create, init)
fun text(c: ComponentContext, init: Text.Builder.() -> Unit) = componentBuilder(c, Text::create, init)
fun verticalScroll(c: ComponentContext, init: VerticalScroll.Builder.() -> Unit) = componentBuilder(c, VerticalScroll::create, init)
