package mobi.sevenwinds.app.budget

import mobi.sevenwinds.app.author.AuthorTable
import mobi.sevenwinds.app.author.findByAuthorId
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object BudgetTable : IntIdTable("budget") {
    val year = integer("year")
    val month = integer("month")
    val amount = integer("amount")
    val type = enumerationByName("type", 100, BudgetType::class)
    val authorId = integer("author_id").references(AuthorTable.id).uniqueIndex().nullable()
}

class BudgetEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BudgetEntity>(BudgetTable)

    var year by BudgetTable.year
    var month by BudgetTable.month
    var amount by BudgetTable.amount
    var type by BudgetTable.type
    var authorId by BudgetTable.authorId

    fun toResponse(): BudgetRecord {
        val authorInfo = authorId?.let { findByAuthorId(it) }

        return BudgetRecord(year, month, amount, type, authorId, authorInfo)
    }
}
