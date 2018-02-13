package org.minicluster.splitters

import org.minicluster.splitters.tasks.SplittableTask

interface Splitter {

    fun <T> split(task: SplittableTask<T>, action: (SplittableTask<T>) -> T ) : T

}