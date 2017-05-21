package Model

import View.Observer

interface Observable {

    fun addObserver(observer: Observer)
    fun delObserver(observer: Observer)
    fun alertObservers()
}
