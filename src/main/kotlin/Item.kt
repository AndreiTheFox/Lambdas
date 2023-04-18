interface Item {
    var id: Int
    val mutableListMessages: MutableList<Message>
    var markDelete: Boolean
    var text: String //Текст заметки.

}

class ItemNotFoundException(message: String) : RuntimeException(message)