package ru.beauty.bar.database.api

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.database.model.MasterInsert
import ru.beauty.bar.database.model.WorkSchedule
import ru.beauty.bar.database.model.WorkScheduleInsert
import ru.beauty.bar.ui.fragment.admin.masters.AdminEditPortfolio

class MasterRepository(val client: SupabaseClient) {
    suspend fun selectSingleMaster(masterId: Int): Master? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val master = client
                .from("MASTER")
                .select {
                    filter {
                        eq("master_id", masterId)
                    }
                }
                .decodeSingle<Master>()

            return master
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)
            App.INSTANCE.mainInterface.toggleLoading(false)
            return null
        }
    }

    suspend fun selectMasters(): List<MasterScheduleCombined> {
        App.INSTANCE.mainInterface.toggleLoading(true)
        var list: List<MasterScheduleCombined> = emptyList()
        try {
            val masterList = client
                .from("MASTER")
                .select()
                .decodeList<Master>()

            val combinedArray =
                Array<MasterScheduleCombined>(masterList.size) { _ -> MasterScheduleCombined() }

            var i = 0
            for (
            item: Master in masterList
            ) {
                val workSchedule = client
                    .from("WORK_SCHEME")
                    .select {
                        filter {
                            eq("scheme_id", item.workScheduleId)
                        }
                    }.decodeSingle<WorkSchedule>()
                val masterScheduleCombined = MasterScheduleCombined(
                    master = item,
                    schedule = workSchedule
                )
                combinedArray[i] = masterScheduleCombined
                i++
            }

            list = combinedArray.toList()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return list
        } catch (e: Exception) {
            if (e.localizedMessage.contains("dupl")) {
                App.INSTANCE.mainInterface.showErrorMessage("Мастер с таким логином уже существует!")
            }
            //App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)
            App.INSTANCE.mainInterface.toggleLoading(false)
            return list
        }
    }

    suspend fun insertMaster(master: MasterInsert, workSchedule: WorkScheduleInsert): Master? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val schedule = client
                .from("WORK_SCHEME")
                .insert(workSchedule) {
                    select()
                }.decodeSingle<WorkSchedule>()

            val masterFin = MasterInsert(
                login = master.login,
                passhash = master.passhash,
                name = master.name,
                pictueLink = master.pictueLink,
                experience = master.experience,
                workScheduleId = schedule.id,
                portfloio = master.portfloio
            )

            val result = client
                .from("MASTER")
                .insert(masterFin) {
                    select()
                }
                .decodeSingle<Master>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)
            App.INSTANCE.mainInterface.toggleLoading(false)
            return null
        }
    }

    suspend fun updateMaster(
        master: Master,
        workSchedule: WorkSchedule
    ): String? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            client
                .from("WORK_SCHEME")
                .update(workSchedule) {
                    filter {
                        eq("scheme_id", workSchedule.id)
                    }
                }

            client
                .from("MASTER")
                .update(master) {
                    filter {
                        eq("master_id", master.id)
                    }
                }

            val result = ""

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)
            App.INSTANCE.mainInterface.toggleLoading(false)
            return null
        }
    }

    suspend fun updatePortfolio(master: Master, portfolio: String)
    :
    Boolean {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            client
                .from("MASTER")
                .update({
                    set("portfolio", portfolio)
                }) {
                    filter {
                        eq("master_id", master.id)
                    }
                }

            App.INSTANCE.mainInterface.toggleLoading(true)
            return true
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)
            App.INSTANCE.mainInterface.toggleLoading(true)
            return false
        }
    }
}