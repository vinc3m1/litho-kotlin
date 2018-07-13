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

@DslMarker
annotation class LithoMarker

/** A concrete class to help scope the DSL. */
class ChildHolder {
  val children = mutableListOf<Component.Builder<*>>()
}

/**
 * Base implementation for components.
 * @param c the {@link ComponentContext} needed to create this builder.
 * @param create the creator function reference. E.g. Text::create.
 * @param init the DSL builder lambda.
 */
@LithoMarker
fun <B : Component.Builder<B>> componentBuilder(
    c: ComponentContext,
    create: (c: ComponentContext) -> B,
    init: B.() -> Unit): Component.Builder<B> {
  val builder = create(c)
  builder.init()
  return builder
}

/**
 * Base implementation for components as children of containers.
 * @param c the {@link ComponentContext} needed to create this builder.
 * @param create the creator function reference. E.g. Text::create.
 * @param init the DSL builder lambda.
 */
@LithoMarker
fun <B : Component.Builder<B>> ChildHolder.componentBuilder(
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
fun <B : Component.ContainerBuilder<B>> Component.ContainerBuilder<B>.children(
    init: ChildHolder.() -> Unit) {
  val childHolder = ChildHolder()
  childHolder.init()
  for (child in childHolder.children) {
    this.child(child)
  }
}

/** Containers */

@LithoMarker
fun column(c: ComponentContext, init: Column.Builder.() -> Unit) =
    componentBuilder(c, Column::create, init)

@LithoMarker
fun ChildHolder.column(c: ComponentContext, init: Column.Builder.() -> Unit) =
    componentBuilder(c, Column::create, init)

@LithoMarker
fun row(c: ComponentContext, init: Row.Builder.() -> Unit) =
    componentBuilder(c, Row::create, init)

@LithoMarker
fun ChildHolder.row(c: ComponentContext, init: Row.Builder.() -> Unit) =
    componentBuilder(c, Row::create, init)

/** Components */

@LithoMarker
fun card(c: ComponentContext, init: Card.Builder.() -> Unit) =
    componentBuilder(c, Card::create, init)

@LithoMarker
fun ChildHolder.card(c: ComponentContext, init: Card.Builder.() -> Unit) =
    componentBuilder(c, Card::create, init)

@LithoMarker
fun cardClip(c: ComponentContext, init: CardClip.Builder.() -> Unit) =
    componentBuilder(c, CardClip::create, init)

@LithoMarker
fun ChildHolder.cardClip(c: ComponentContext, init: CardClip.Builder.() -> Unit) =
    componentBuilder(c, CardClip::create, init)

@LithoMarker
fun editText(c: ComponentContext, init: EditText.Builder.() -> Unit) =
    componentBuilder(c, EditText::create, init)

@LithoMarker
fun ChildHolder.editText(c: ComponentContext, init: EditText.Builder.() -> Unit) =
    componentBuilder(c, EditText::create, init)

@LithoMarker
fun empty(c: ComponentContext, init: EmptyComponent.Builder.() -> Unit) =
    componentBuilder(c, EmptyComponent::create, init)

@LithoMarker
fun ChildHolder.empty(c: ComponentContext, init: EmptyComponent.Builder.() -> Unit) =
    componentBuilder(c, EmptyComponent::create, init)

@LithoMarker
fun horizontalScroll(c: ComponentContext, init: HorizontalScroll.Builder.() -> Unit) =
    componentBuilder(c, HorizontalScroll::create, init)

@LithoMarker
fun ChildHolder.horizontalScroll(c: ComponentContext, init: HorizontalScroll.Builder.() -> Unit) =
    componentBuilder(c, HorizontalScroll::create, init)

@LithoMarker
fun image(c: ComponentContext, init: Image.Builder.() -> Unit) =
    componentBuilder(c, Image::create, init)

@LithoMarker
fun ChildHolder.image(c: ComponentContext, init: Image.Builder.() -> Unit) =
    componentBuilder(c, Image::create, init)

@LithoMarker
fun lazySelector(c: ComponentContext, init: LazySelectorComponent.Builder.() -> Unit) =
    componentBuilder(c, LazySelectorComponent::create, init)

@LithoMarker
fun ChildHolder.progress(c: ComponentContext, init: Progress.Builder.() -> Unit) =
    componentBuilder(c, Progress::create, init)

@LithoMarker
fun progress(c: ComponentContext, init: Progress.Builder.() -> Unit) =
    componentBuilder(c, Progress::create, init)

@LithoMarker
fun ChildHolder.lazySelector(c: ComponentContext, init: LazySelectorComponent.Builder.() -> Unit) =
    componentBuilder(c, LazySelectorComponent::create, init)

@LithoMarker
fun recycler(c: ComponentContext, init: Recycler.Builder.() -> Unit) =
    componentBuilder(c, Recycler::create, init)

@LithoMarker
fun ChildHolder.recycler(c: ComponentContext, init: Recycler.Builder.() -> Unit) =
    componentBuilder(c, Recycler::create, init)

@LithoMarker
fun recyclerCollectionComponent(c: ComponentContext,
                                init: RecyclerCollectionComponent.Builder.() -> Unit) =
    componentBuilder(c, RecyclerCollectionComponent::create, init)

@LithoMarker
fun ChildHolder.recyclerCollectionComponent(c: ComponentContext,
                                            init: RecyclerCollectionComponent.Builder.() -> Unit) =
    componentBuilder(c, RecyclerCollectionComponent::create, init)

@LithoMarker
fun selector(c: ComponentContext, init: SelectorComponent.Builder.() -> Unit) =
    componentBuilder(c, SelectorComponent::create, init)

@LithoMarker
fun ChildHolder.selector(c: ComponentContext, init: SelectorComponent.Builder.() -> Unit) =
    componentBuilder(c, SelectorComponent::create, init)

@LithoMarker
fun solidColor(c: ComponentContext, init: SolidColor.Builder.() -> Unit) =
    componentBuilder(c, SolidColor::create, init)

@LithoMarker
fun ChildHolder.solidColor(c: ComponentContext, init: SolidColor.Builder.() -> Unit) =
    componentBuilder(c, SolidColor::create, init)

@LithoMarker
fun text(c: ComponentContext, init: Text.Builder.() -> Unit) =
    componentBuilder(c, Text::create, init)

@LithoMarker
fun ChildHolder.text(c: ComponentContext, init: Text.Builder.() -> Unit) =
    componentBuilder(c, Text::create, init)

@LithoMarker
fun verticalScroll(c: ComponentContext, init: VerticalScroll.Builder.() -> Unit) =
    componentBuilder(c, VerticalScroll::create, init)

@LithoMarker
fun ChildHolder.verticalScroll(c: ComponentContext, init: VerticalScroll.Builder.() -> Unit) =
    componentBuilder(c, VerticalScroll::create, init)
