/*
 */

package com.gtoretti.viajasp.data.equityChangesStatement

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquityChangesStatementRepository @Inject constructor(private val equityChangesStatementDao: EquityChangesStatementDao) {

    fun getEquityChangesStatements() = equityChangesStatementDao.getActiveEquityChangesStatements()

    fun getEquityChangesStatementItems(id: Long) = equityChangesStatementDao.getEquityChangesStatementItems(id)

    suspend fun saveEquityChangesStatement(equityChangesStatement: EquityChangesStatement){
        equityChangesStatementDao.upsert(equityChangesStatement)
    }

    suspend fun deleteEquityChangesStatement(equityChangesStatement: EquityChangesStatement){
        equityChangesStatementDao.delete(equityChangesStatement.equityChangesStatementId)
    }

    suspend fun saveEquityChangesStatementItem(equityChangesStatementItem: EquityChangesStatementItem){
        equityChangesStatementDao.upsert(equityChangesStatementItem)
    }

    suspend fun deleteEquityChangesStatementItem(equityChangesStatementItem: EquityChangesStatementItem){
        equityChangesStatementDao.deleteItem(equityChangesStatementItem.equityChangesStatementItemId)
    }
}
