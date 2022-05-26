package com.example.habits.domain.models.mapper

interface ViewObjectMapper<VO, DTO> {

    fun toViewObject(dto: DTO): VO

    fun toViewObject(list: Collection<DTO>): List<VO> {
        val result = ArrayList<VO>()
        list.mapTo(result) { toViewObject(it) }
        return result
    }

    fun toDataTransferObject(vo: VO): DTO

    fun toDataTransferObject(list: Collection<VO>): List<DTO> {
        val result = ArrayList<DTO>()
        list.mapTo(result) { toDataTransferObject(it) }
        return result
    }
}