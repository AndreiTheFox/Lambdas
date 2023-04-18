abstract class Service<T : Item> {
    private val items = mutableListOf<T>()
    private var indexInList: Int =0
    //Добавление (чата/заметки/поста) - одна и та же сущность с разными типами объектов, входящий тип объекта в функцию - любой
    fun add(elem: T): T {
        elem.id = indexInList
        items += elem
        indexInList += 1
        return items.last()
    }

    //Добавление К (чату/заметке/посту) (с указанным ID) - сообщения в список сообщений
    fun addMessage(elemId: Int, message: Message): Message {
        val elem = items.find { it.id == elemId } ?: throw ItemNotFoundException("Item ID: $elemId not found in List")
        elem.mutableListMessages.add(message)
        return elem.mutableListMessages.last()  //Возвращает последнее добавленное сообщение
    }

    //Удаление (чата/заметки/поста) - проставление пометки об удалении у объекта
    fun deleteItem(elemId: Int): T {
        val item = items.find { it.id == elemId } ?: throw ItemNotFoundException("Item ID: $elemId not found in List")
        item.markDelete = true
        return items[elemId]
    }

    //Редактирование чата/заметки/поста - необходим параметр text у прилетающего в функцию объекта
    fun editItem(elemId: Int, newText: String): T {
        val item = items.find { it.id == elemId } ?: throw ItemNotFoundException("Item ID: $elemId not found in List")
        item.text = newText
        return items[elemId]
    }

    //Возвращает (чат/заметку/пост) по ID
    fun getItemById(elemId: Int): T {
        return items.find { it.id == elemId } ?: throw ItemNotFoundException("Item ID: $elemId not found in List")
    }

    //Возвращает список всех (чатов/заметок/постов)
    fun getItems(): MutableList<T> {
        return items
    }

    //Возвращает список сообщений к (чату/заметке/посту)
    fun getMessages(elemId: Int): MutableList<Message> {
        val item =
            items.find { it.id == elemId } ?: throw ItemNotFoundException("Item ID: $elemId not found in List")   //Нашли item
        return item.mutableListMessages //Вернули лист сообщений к item
    }

    //Редактирование сообщения у (чата/заметки/поста)
    fun editMessage(elemId: Int, messageId: Int, newText: String): Message {
        val item = getItemById(elemId)
        val messageToEdit = item.mutableListMessages.find { it.id == messageId }
            ?: throw MessageNotFoundException("Сообщение или комментарий не найдены")
        messageToEdit.content = newText
        return item.mutableListMessages[messageId]
    }

    //Удаление сообщение у (чата/заметки/поста) - проставление пометки об удалении у объекта
    fun deleteMessage(
        elemId: Int,
        messageId: Int,
    ): Message {
        val item: T = getItemById(elemId)   //Нашли элемент
        val messageToEdit = item.mutableListMessages.find { it.id == messageId }
            ?: throw MessageNotFoundException("Сообщение или комментарий не найдены")
        messageToEdit.markForDelete = true
        return item.mutableListMessages[messageId]    //Возвращает комментарий с проставленной отметкой об удалении
    }
}