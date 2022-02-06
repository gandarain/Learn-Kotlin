package app

import data.BigNote
import data.Note

fun main() {
    var note = Note("Belajar Kotlin")
    println(note.title)

    note.title = ""
    println(note.title)

    var bigNote = BigNote("Belajar Kotlin")
    println(bigNote.title)
    println(bigNote.bigTitle)
}