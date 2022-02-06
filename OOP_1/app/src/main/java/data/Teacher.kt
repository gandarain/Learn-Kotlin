package data

open class Teacher(val name: String) {
    // only access inside class
    private fun teach() {
        println("Teach")
    }

    // only access on child
    protected open fun test() {
        println("Test")
    }

    fun write() {
        println("Write")
    }
}

class SuperTeacher(name: String): Teacher(name) {
    public override fun test(){
        super.test()
    }
}