data class Post (       //Класс Постов пользователя
    override var id: Int,
    override val mutableListMessages: MutableList<Message>, //Список сообщений/комментов поста
    override var markDelete: Boolean,                       //Пометка об удалении
    override var text: String,                         //Текст поста
) : Item