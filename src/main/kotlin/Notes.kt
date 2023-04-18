data class Notes (       //Класс заметок пользователя
    override var id: Int,
    override val mutableListMessages: MutableList<Message>, //Список сообщений заметки
    override var markDelete: Boolean,       //Пометка об удалении
    override var text: String = "",       //Текст заметки
) : Item