package org.minicluster.splitters.tasks

import com.github.salomonbrys.kodein.Kodein

interface SplittableTask<T> {

    val arguments: Map<String, Any?>
    val kodein: Kodein
    fun split(): List<SplittableTask<T>>

    fun merge(tasksResults: List<T>): T

}