package data

class Company(val name: String) {
    override fun equals(other: Any?): Boolean {
        return when(other) {
            is Company -> this.name == other.name
            else -> false
        }
    }

    override fun hashCode(): Int {
        return this.name.hashCode()
    }
}