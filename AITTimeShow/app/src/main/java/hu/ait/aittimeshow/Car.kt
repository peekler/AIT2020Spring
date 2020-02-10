package hu.ait.aittimeshow

class Car(var type: String){


    init {
        type = type.toUpperCase()
        //...
    }

    var avM = 0

    constructor(type: String, avM: Int) : this(type){
        this.avM = avM
    }


    fun showType() : String{

        return type
    }
}