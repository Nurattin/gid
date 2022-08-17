package com.travel.gid.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Events(
    @SerialName("data")
    val `data`: List<Event>,
    @SerialName("links")
    val links: Links,
    @SerialName("meta")
    val meta: Meta
) {
    @Serializable
    data class Event(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("stars")
        val stars: Int,
        @SerialName("geo")
        val geo: Geo,
        @SerialName("image")
        val image: String,
        @SerialName("detailImages")
        val detailImages: List<String>,
        @SerialName("eventCategories")
        val eventCategories: List<EventCategory>,
        @SerialName("city")
        val city: City,
        @SerialName("startAt")
        val startAt: String,
        @SerialName("endAt")
        val endAt: String
    ) {
        @Serializable
        data class Geo(
            @SerialName("lat")
            val lat: Double,
            @SerialName("lng")
            val lng: Double
        )

        @Serializable
        data class EventCategory(
            @SerialName("id")
            val id: Int,
            @SerialName("name")
            val name: String
        )

        @Serializable
        data class City(
            @SerialName("id")
            val id: Int,
            @SerialName("name")
            val name: String
        )
    }

    @Serializable
    data class Links(
        @SerialName("first")
        val first: String,
        @SerialName("last")
        val last: String,
        @SerialName("prev")
        val prev: Int?,
        @SerialName("next")
        val next: Int?
    )

    @Serializable
    data class Meta(
        @SerialName("current_page")
        val currentPage: Int,
        @SerialName("from")
        val from: Int,
        @SerialName("last_page")
        val lastPage: Int,
        @SerialName("links")
        val links: List<Link>,
        @SerialName("path")
        val path: String,
        @SerialName("per_page")
        val perPage: Int,
        @SerialName("to")
        val to: Int,
        @SerialName("total")
        val total: Int
    ) {
        @Serializable
        data class Link(
            @SerialName("url")
            val url: String?,
            @SerialName("label")
            val label: String,
            @SerialName("active")
            val active: Boolean
        )
    }
}