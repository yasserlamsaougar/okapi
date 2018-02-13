package org.minicluster.splitters

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.minicluster.splitters.tasks.SplittableTask

class SimpleSplitter : Splitter {

    override fun <T> split(task: SplittableTask<T>, action: (SplittableTask<T>) -> T): T {

        val tasks = task.split()
        val deferred = tasks.map {
            async {
                action(it)
            }
        }
        return runBlocking {
            task.merge(deferred.map{it.await()})
        }
    }

}