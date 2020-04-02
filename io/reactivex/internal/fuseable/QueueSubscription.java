package io.reactivex.internal.fuseable;

import org.reactivestreams.Subscription;

public abstract interface QueueSubscription<T>
  extends QueueFuseable<T>, Subscription
{}
