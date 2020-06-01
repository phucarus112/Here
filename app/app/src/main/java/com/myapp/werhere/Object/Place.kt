package com.myapp.werhere.Object

class Place {
    var id: String = ""
    var place_id: String = ""
    var lat: Double =0.0
    var lng: Double = 0.0
    var name: String =""
    var icon: String=""
    var rating: Double = 0.0
    var total_rating_turns: Int = 0
    var address: String=""

    constructor()

    constructor(
        id: String,
        place_id: String,
        lat: Double,
        lng: Double,
        name: String,
        icon: String,
        rating: Double,
        total_rating_turns: Int,
        address: String
    ) {
        this.id = id
        this.place_id = place_id
        this.lat = lat
        this.lng = lng
        this.name = name
        this.icon = icon
        this.rating = rating
        this.total_rating_turns = total_rating_turns
        this.address = address
    }
}