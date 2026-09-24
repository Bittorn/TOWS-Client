package com.windtempos.tows

class GameManager private constructor() {
    companion object {

        @Volatile // ensure instance property is updated atomically
        private var instance: GameManager? = null

        fun getInstance() =
            instance ?: synchronized(this) { // prevent accessing method from multiple threads simultaneously
                instance ?: GameManager().also { instance = it }
            }
    }

    fun doSomething() = "Doing something"
}